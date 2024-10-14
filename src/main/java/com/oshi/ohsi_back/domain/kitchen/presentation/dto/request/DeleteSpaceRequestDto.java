package com.oshi.ohsi_back.domain.kitchen.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteSpaceRequestDto {

    @NotNull(message = "Space ID is required")
    private Long spaceId;

    // Getters and setters
}