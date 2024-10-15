package com.oshi.ohsi_back.domain.tag.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oshi.ohsi_back.domain.tag.application.TagService;
import com.oshi.ohsi_back.domain.tag.presentation.dto.response.TagResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 모든 태그를 반환하는 엔드포인트
    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        List<TagResponseDto> tags = tagService.getAllTags();  // 서비스 메서드 호출
        return ResponseEntity.ok(tags);  // 태그 목록 반환
    }
}