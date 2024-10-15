package com.oshi.ohsi_back.domain.tag.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.oshi.ohsi_back.domain.tag.infrastructure.TagRepository;
import com.oshi.ohsi_back.domain.tag.domain.entity.Tag;
import com.oshi.ohsi_back.domain.tag.presentation.dto.response.TagResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    // 모든 태그 객체를 반환하는 메서드
    public List<TagResponseDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();  // 태그 목록 조회

        // Tag 엔티티를 TagResponseDto로 변환
        return tags.stream()
                .map(tag -> new TagResponseDto(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }
}