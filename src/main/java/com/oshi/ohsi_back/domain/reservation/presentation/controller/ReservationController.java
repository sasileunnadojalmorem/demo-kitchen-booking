package com.oshi.ohsi_back.domain.reservation.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.oshi.ohsi_back.domain.reservation.application.ReservationService;
import com.oshi.ohsi_back.domain.reservation.presentation.dto.request.ReservationRequestDto;
import com.oshi.ohsi_back.domain.reservation.presentation.dto.response.ReservationResponseDto;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 생성
    @PostMapping
    public ResponseEntity<Long> createReservation(@AuthenticationPrincipal User user,
                                                  @Valid @RequestBody ReservationRequestDto requestDto) {
        Long reservationId = reservationService.createReservation(user, requestDto);
        return ResponseEntity.status(201).body(reservationId);
    }

    // 예약 삭제
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@AuthenticationPrincipal User user,
                                                  @PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId, user);
        return ResponseEntity.noContent().build();  // HTTP 204 No Content
    }

    // 내 예약 목록 보기
    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponseDto>> getMyReservations(@AuthenticationPrincipal User user) {
        List<ReservationResponseDto> reservations = reservationService.getMyReservations(user);
        return ResponseEntity.ok(reservations);
    }

    // 단일 예약 조회
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable Long reservationId) {
        ReservationResponseDto reservation = reservationService.getReservation(reservationId);
        return ResponseEntity.ok(reservation);
    }
}
