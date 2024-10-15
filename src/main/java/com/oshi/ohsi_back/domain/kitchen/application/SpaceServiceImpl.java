package com.oshi.ohsi_back.domain.kitchen.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.domain.kitchen.domain.entity.Space;
import com.oshi.ohsi_back.domain.kitchen.infrastructure.SpaceRepository;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.AddSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.DeleteSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.GetSpaceListDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.SearchSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.UpdateSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.response.SpaceResponseDto;
import com.oshi.ohsi_back.domain.tag.domain.entity.SpaceTag;
import com.oshi.ohsi_back.domain.tag.domain.entity.Tag;
import com.oshi.ohsi_back.domain.tag.infrastructure.SpaceTagRepository;
import com.oshi.ohsi_back.domain.tag.infrastructure.TagRepository;
import com.oshi.ohsi_back.domain.tag.presentation.dto.response.TagResponseDto;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;
import com.oshi.ohsi_back.domain.user.execption.UserException;
import com.oshi.ohsi_back.domain.user.infrastructure.UserRepository;
import com.oshi.ohsi_back.domain.image.application.Fileservice;
import com.oshi.ohsi_back.domain.image.domain.entity.ImageEntity;
import com.oshi.ohsi_back.domain.image.infrastructure.ImageRepository;
import com.oshi.ohsi_back.exception.exceptionclass.CustomException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final Fileservice fileService;
    private final SpaceTagRepository spaceTagRepository;
    private final TagRepository tagRepository;
    @Override
@Transactional
public Long addSpace(User owner, AddSpaceRequestDto requestDto, HttpServletRequest request) {
    // owner 객체가 null인지 확인
    if (owner == null) {
        log.error("Owner 객체가 null입니다.");
        throw new CustomException(ErrorCode.NOT_EXISTED_USER);
    }

    // owner 객체의 id가 null인지 확인
    if (owner.getId() == null) {
        log.error("Owner ID가 null입니다.");
        throw new CustomException(ErrorCode.NOT_EXISTED_USER);
    }

    // owner ID 값이 유효한지 확인 (DB에서 해당 ID로 User가 존재하는지 확인)
    User ownerEntity = userRepository.findById(owner.getId())
                       .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTED_USER));

    // DTO를 사용해 Space 엔티티 생성
    Space space = Space.fromDto(requestDto, ownerEntity);

    // Space 먼저 저장
    Space savedSpace = spaceRepository.save(space);  

    // 태그 리스트 저장
    List<Long> tagList = requestDto.getTagList();
    if (tagList != null) {
        for (Long tagId : tagList) {
            Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new CustomException(ErrorCode.DATABASE_ERROR));  // 태그가 존재하는지 확인

            // SpaceTag 엔티티 생성 후 저장
            SpaceTag spaceTag = new SpaceTag();
            spaceTag.setSpace(savedSpace);
            spaceTag.setTag(tag);
            spaceTagRepository.save(spaceTag);
        }
    }

    // 이미지 처리
    MultipartFile file = requestDto.getFile();
    if (file != null && !file.isEmpty()) {
        log.info("Processing image for Space with ID: {}", space.getId());

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUrl("temporary-url");  // 임시 URL 설정

        imageRepository.save(imageEntity);  // 이미지 엔티티 저장

        // 파일 저장 및 URL 업데이트
        String imageUrl = fileService.SaveImage(file);
        if (imageUrl == null) {
            log.error("Image saving failed for Space ID: {}", space.getId());
            throw new CustomException(ErrorCode.DATABASE_ERROR, "Image saving failed");
        }

        imageEntity.setUrl(imageUrl);
        imageRepository.save(imageEntity);  // 최종 URL 저장
        space.setImage(imageEntity);  // Space에 이미지 설정
    }

    // Space 저장 완료
    log.info("Space 저장 완료 - Space ID: {}", space.getId());

    return space.getId();
}

    @Override
@Transactional
public void updateSpace(User owner, Long spaceId, UpdateSpaceRequestDto requestDto) {
    // 이메일로 사용자 조회
    User user = userRepository.findByEmail(owner.getEmail())
            .orElseThrow(() -> new UserException(ErrorCode.NOT_EXISTED_USER));

    // Space 엔티티 조회
    Space space = spaceRepository.findById(spaceId)
            .orElseThrow(() -> new IllegalArgumentException("Space not found"));

    // 소유자 이메일 확인
    if (!space.getOwner().getEmail().equals(owner.getEmail())) {
        log.error("권한 없는 사용자가 Space를 수정하려고 시도했습니다.");
        throw new UserException(ErrorCode.SECURITY_ACCESS_DENIED);
    }

    // Space 엔티티 수정
    space.setName(requestDto.getName());
    space.setLocation(requestDto.getLocation());
    space.setDescription(requestDto.getDescription());
    space.setPrice(requestDto.getPrice());
    space.setStartTime(requestDto.getStartTime());
    space.setEndTime(requestDto.getEndTime());
    space.setCapacity(requestDto.getCapacity());

    // 이미지 처리
    MultipartFile file = requestDto.getFile();  // 이미지 파일 가져오기
    if (file != null && !file.isEmpty()) {
        log.info("Updating image for Space with ID: {}", space.getId());

        ImageEntity imageEntity = space.getImage(); // Space에 연관된 ImageEntity 가져오기
        if (imageEntity == null) {
            // 이미지가 없으면 새로운 ImageEntity 생성
            imageEntity = new ImageEntity();
            space.setImage(imageEntity); // Space에 이미지 설정
        }

        // 이미지 저장 및 URL 업데이트
        String imageUrl = fileService.SaveImage(file); // 실제 이미지 저장
        if (imageUrl == null) {
            log.error("Image saving failed for Space ID: {}", space.getId());
            throw new CustomException(ErrorCode.DATABASE_ERROR, "Image saving failed");
        }

        imageEntity.setUrl(imageUrl); // 이미지 URL 업데이트
        imageRepository.save(imageEntity); // ImageEntity 저장
    }

    // 수정된 Space 객체 저장
    spaceRepository.save(space);

    log.info("Space 수정 완료 - Space ID: {}", space.getId());
}

    @Override
    @Transactional
    public void deleteSpace(User owner, DeleteSpaceRequestDto requestDto) {
        // Space 엔티티 삭제
        Space space = spaceRepository.findById(requestDto.getSpaceId())
                .orElseThrow(() -> new IllegalArgumentException("Space not found"));

        // 소유자 이메일 확인 후 삭제
        if(space.getOwner().getEmail().equals(owner.getEmail())) {
            spaceRepository.delete(space);
            log.info("Space 삭제 완료 - Space ID: {}", space.getId());
        } else {
            log.error("권한 없는 사용자가 Space를 삭제하려고 시도했습니다.");
            throw new UserException(ErrorCode.SECURITY_ACCESS_DENIED);  // 권한이 없을 경우 예외 처리
        }
    }

    @Override
    public Page<SpaceResponseDto> searchSpaces(SearchSpaceRequestDto searchDto) {
        // Pageable 객체 생성 (page와 size 값을 이용)
        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize());

        // 태그 ID 리스트로 페이징 처리된 공간 목록 조회
        Page<Space> spaces = spaceRepository.findByKeyword(searchDto.getKeyword(), pageable);

        // 변환 로직: Page<Space> -> Page<SpaceResponseDto>
        return spaces.map(this::convertToDto);
    }

    @Override
    public List<SpaceResponseDto> findSpacesByOwner(User owner) {
        // 최대 6개의 최신 순으로 페이징된 사용자 등록 장소 반환
        Pageable pageable = PageRequest.of(0, 6);
        List<Space> spaces = spaceRepository.findByOwnerOrderByCreatedAtDesc(owner, pageable);

        return spaces.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Space -> SpaceResponseDto 변환 메서드
    private SpaceResponseDto convertToDto(Space space) {
        SpaceResponseDto dto = new SpaceResponseDto();
        dto.setId(space.getId());
        dto.setName(space.getName());
        dto.setLocation(space.getLocation());
        dto.setDescription(space.getDescription());
        dto.setPrice(space.getPrice());
        dto.setStartTime(space.getStartTime());
        dto.setEndTime(space.getEndTime());
        dto.setCapacity(space.getCapacity());
        dto.setCreatedAt(space.getCreatedAt());
        if(space.getImage()!= null){
            dto.setImageUrl(space.getImage().getUrl());
 
        }
        List<TagResponseDto> tagResponseDtos = space.getSpaceTags().stream()
        .map(spaceTag -> new TagResponseDto(spaceTag.getTag().getId(), spaceTag.getTag().getName()))
        .collect(Collectors.toList());
        dto.setTags(tagResponseDtos);

        if(space.getReservations() != null){
            dto.setReservationCheck(true);
        } // 예약 리스트가 비어있지 않으면 예약됨
        else{
            dto.setReservationCheck(false);
        }

        return dto;
    }

    @Override
    public SpaceResponseDto findSpaceById(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
               .orElseThrow(() -> new IllegalArgumentException("Space not found"));

        return convertToDto(space);
    }

    @Override
    public Page<SpaceResponseDto> findAllSpacesByPage(GetSpaceListDto requestdto) {
        Pageable pageable = PageRequest.of(requestdto.getPage(), requestdto.getSize());
        Page<Space> spaces = spaceRepository.findAll(pageable);
        return spaces.map(this::convertToDto);

    }
}