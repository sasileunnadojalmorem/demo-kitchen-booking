package com.oshi.ohsi_back.domain.kitchen.presentation.dto.request;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchSpaceRequestDto {

    private List<Long> tagIds;  // 검색에 사용할 태그 ID 리스트
    private int page;  // 페이지 번호
    private int size;  // 페이지당 결과 개수

    // Getters and Setters
}
