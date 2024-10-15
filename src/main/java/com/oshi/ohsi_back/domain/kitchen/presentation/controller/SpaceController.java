package com.oshi.ohsi_back.domain.kitchen.presentation.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.AddSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.DeleteSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.GetSpaceListDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.UpdateSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.SearchSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.response.SpaceResponseDto;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;
import com.oshi.ohsi_back.domain.kitchen.application.SpaceService;

import java.util.List;

@RestController
@RequestMapping("/api/spaces")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    // 새로운 장소 추가
    @PostMapping
    public ResponseEntity<Long> addSpace(@AuthenticationPrincipal User owner,
                                         @Valid @ModelAttribute AddSpaceRequestDto requestDto,  // @ModelAttribute로 변경하여 MultipartFile 지원
                                         HttpServletRequest request) {
        Long spaceId = spaceService.addSpace(owner, requestDto, request);
        return ResponseEntity.status(201).body(spaceId);  // HTTP 201 Created
    }

    // 장소 정보 수정
    @PutMapping("/{spaceId}")
    public ResponseEntity<Void> updateSpace(@AuthenticationPrincipal User owner,
                                            @PathVariable Long spaceId,
                                            @Valid @ModelAttribute UpdateSpaceRequestDto requestDto,  // @ModelAttribute로 변경하여 MultipartFile 지원
                                            HttpServletRequest request) {
        spaceService.updateSpace(owner, spaceId, requestDto);
        return ResponseEntity.ok().build();  // HTTP 200 OK
    }

    // 장소 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteSpace(@AuthenticationPrincipal User owner,
                                            @Valid @RequestBody DeleteSpaceRequestDto requestDto,
                                            HttpServletRequest request) {
        spaceService.deleteSpace(owner, requestDto);
        return ResponseEntity.noContent().build();  // HTTP 204 No Content
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<SpaceResponseDto> getSpace(@AuthenticationPrincipal User owner,
                                                    @PathVariable Long spaceId){
        SpaceResponseDto dto=spaceService.findSpaceById(spaceId);
        return ResponseEntity.ok().body(dto);
                                                    }
    @GetMapping("/spaceList")
    public ResponseEntity<Page<SpaceResponseDto>> getAllSpaces(@Valid@RequestBody GetSpaceListDto dto) {
        Page<SpaceResponseDto> spaces = spaceService.findAllSpacesByPage(dto);
        return ResponseEntity.ok(spaces);  // HTTP 200 OK
    }                
    // 사용자가 등록한 장소 목록 조회 (최대 6개, 최신순)
    @GetMapping("/my-spaces")
    public ResponseEntity<List<SpaceResponseDto>> getMySpaces(@AuthenticationPrincipal User owner) {
        List<SpaceResponseDto> spaces = spaceService.findSpacesByOwner(owner);
        return ResponseEntity.ok(spaces);  // HTTP 200 OK
    }

    // 태그를 기반으로 장소 검색 (페이징 지원)
    @PostMapping("/search")
    public ResponseEntity<Page<SpaceResponseDto>> searchSpacesByTags(
            @Valid @RequestBody SearchSpaceRequestDto searchDto) {
        Page<SpaceResponseDto> spaces = spaceService.searchSpaces(searchDto);
        return ResponseEntity.ok(spaces);  // HTTP 200 OK
    }
}