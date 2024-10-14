package com.oshi.ohsi_back.domain.kitchen.presentation.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.oshi.ohsi_back.domain.tag.presentation.dto.response.TagResponseDto;

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
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private LocalDateTime createdAt;

}
