package com.oshi.ohsi_back.domain.reservation.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequestDto {

    @NotNull
    private Long spaceId;


    @NotNull
    private LocalDate reservationDate;  // 예약 날짜
}
