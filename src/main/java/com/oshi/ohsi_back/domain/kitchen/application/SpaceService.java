package com.oshi.ohsi_back.domain.kitchen.application;

import java.util.List;

import org.springframework.data.domain.Page;

import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.AddSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.DeleteSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.GetSpaceListDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.SearchSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.UpdateSpaceRequestDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.response.SpaceResponseDto;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;

import jakarta.servlet.http.HttpServletRequest;

public interface SpaceService {

    Long addSpace(User owner, AddSpaceRequestDto requestDto, HttpServletRequest request);

    void updateSpace(User owner, Long spaceId, UpdateSpaceRequestDto requestDto);

    void deleteSpace(User owner, DeleteSpaceRequestDto requestDto);
    Page<SpaceResponseDto> searchSpaces(SearchSpaceRequestDto searchDto);  // 태그 기반 장소 검색
    SpaceResponseDto findSpaceById(Long spaceId);
    List<SpaceResponseDto> findSpacesByOwner(User owner);  // 사용자가 등록한 장소 조회
    Page<SpaceResponseDto> findAllSpacesByPage(GetSpaceListDto requestdto);
}
