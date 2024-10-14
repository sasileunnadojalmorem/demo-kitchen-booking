package com.oshi.ohsi_back.domain.kitchen.presentation.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceResponseDto {

    private Long id;
    private String name;
    private String location;
    private String description;
    private BigDecimal price;

    // Getters and Setters
}
