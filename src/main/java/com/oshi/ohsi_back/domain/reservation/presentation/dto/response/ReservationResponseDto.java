package com.oshi.ohsi_back.domain.reservation.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.oshi.ohsi_back.domain.kitchen.presentation.dto.response.SpaceResponseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationResponseDto {

    private Long id;
    private LocalDate reservationDate;
    private LocalDateTime createdAt;  // 예약 생성 시간

    private SpaceResponseDto space;  // Space 정보를 포함
}
