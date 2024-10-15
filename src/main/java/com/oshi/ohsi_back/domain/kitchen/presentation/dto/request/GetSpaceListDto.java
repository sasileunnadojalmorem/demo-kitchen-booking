package com.oshi.ohsi_back.domain.kitchen.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetSpaceListDto {

    @NotNull
    private int page;  // 페이지 번호
    @NotNull
    private int size;  // 페이지당 결과 개수

    // Getters and Setters
}
