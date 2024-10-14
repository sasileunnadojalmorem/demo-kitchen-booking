package com.oshi.ohsi_back.domain.kitchen.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.domain.kitchen.domain.entity.Space;
import com.oshi.ohsi_back.domain.kitchen.infrastructure.SpaceRepository;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.AddSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.DeleteSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.SearchSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.UpdateSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.response.SpaceResponseDto;
import com.oshi.ohsi_back.domain.tag.infrastructure.SpaceTagRepository;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;
import com.oshi.ohsi_back.domain.user.execption.UserException;
import com.oshi.ohsi_back.domain.user.infrastructure.UserRepository;
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
    private final SpaceTagRepository  spaceTagRepository;
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
    Space space = new Space(requestDto, ownerEntity);

    spaceRepository.save(space);

    log.info("Space 저장 완료 - Space ID: {}", space.getId());

    return space.getId();
}
    @Override
    public void updateSpace(User owner, Long spaceId, UpdateSpaceRequestDto requestDto) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(owner.getEmail())
                .orElseThrow(() -> new UserException(ErrorCode.NOT_EXISTED_USER));

        // Space 엔티티 수정
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new IllegalArgumentException("Space not found"));

        space.setOwner(user);  // 이메일로 조회된 사용자로 설정
        space.setName(requestDto.getName());
        space.setLocation(requestDto.getLocation());
        space.setDescription(requestDto.getDescription());
        space.setPrice(requestDto.getPrice());
        space.setStartTime(requestDto.getStartTime());
        space.setEndTime(requestDto.getEndTime());
        space.setCapacity(requestDto.getCapacity());

        spaceRepository.save(space);
    }

    @Override
    public void deleteSpace(User owner, DeleteSpaceRequestDto requestDto) {
        // Space 엔티티 삭제
        Space space = spaceRepository.findById(requestDto.getSpaceId())
                .orElseThrow(() -> new IllegalArgumentException("Space not found"));

        // 이메일이 같은지 확인 후 삭제
        if(space.getOwner().getEmail().equals(owner.getEmail())) {
            spaceRepository.delete(space);
        } else {
            throw new UserException(ErrorCode.SECURITY_ACCESS_DENIED);  // 권한이 없을 경우 예외 처리
        }
    }

    @Override
    public Page<SpaceResponseDto> findSpacesByTags(SearchSpaceRequestDto searchDto) {
        // Pageable 객체 생성 (page와 size 값을 이용)
        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize());

        // 태그 ID 리스트로 페이징 처리된 공간 목록 조회
        Page<Space> spaces = spaceTagRepository.findSpacesByTagIds(searchDto.getTagIds(), 
                                        searchDto.getTagIds().size(), pageable);

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
        // 필요한 필드를 추가
        return dto;
    }
}