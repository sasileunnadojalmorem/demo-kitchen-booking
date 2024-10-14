package com.oshi.ohsi_back.domain.kitchen.presentation.dto.request;
import java.util.List;

import org.aspectj.weaver.ast.Not;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchSpaceRequestDto {

    @NotNull
    private String keyword;
    @NotNull
    private int page;  // 페이지 번호
    @NotNull
    private int size;  // 페이지당 결과 개수

    // Getters and Setters
}
