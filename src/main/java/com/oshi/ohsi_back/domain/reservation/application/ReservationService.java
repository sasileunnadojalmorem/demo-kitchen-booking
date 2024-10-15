package com.oshi.ohsi_back.domain.reservation.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oshi.ohsi_back.domain.kitchen.domain.entity.Space;
import com.oshi.ohsi_back.domain.kitchen.infrastructure.SpaceRepository;
import com.oshi.ohsi_back.domain.reservation.domain.entity.Reservation;
import com.oshi.ohsi_back.domain.reservation.infrastructure.ReservationRepository;
import com.oshi.ohsi_back.domain.reservation.presentation.dto.request.ReservationRequestDto;
import com.oshi.ohsi_back.domain.reservation.presentation.dto.response.ReservationResponseDto;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.response.SpaceResponseDto;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;
import com.oshi.ohsi_back.exception.exceptionclass.CustomException;
import com.oshi.ohsi_back.core.properties.ErrorCode;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SpaceRepository spaceRepository;

    // 예약 생성
    @Transactional
    public Long createReservation(User user, ReservationRequestDto requestDto) {
        // 공간 찾기
        Space space = spaceRepository.findById(requestDto.getSpaceId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATABASE_ERROR));

        // 중복 예약 방지 (해당 공간과 날짜에 이미 예약이 있는지 확인)
        List<Reservation> existingReservations = reservationRepository.findBySpaceAndReservationDate(space, requestDto.getReservationDate());
        if (!existingReservations.isEmpty()) {
            throw new CustomException(ErrorCode.DATABASE_ERROR);
        }

        // Reservation 엔티티 생성
        Reservation reservation = Reservation.createReservation(space, user, requestDto.getReservationDate());

        // 예약 저장
        reservationRepository.save(reservation);
        space.setReservations(reservation);
        spaceRepository.save(space);
        return reservation.getId();
    }

    // 예약 삭제
    @Transactional
    public void deleteReservation(Long reservationId, User user) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATABASE_ERROR));

        // 예약 소유자만 삭제 가능
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.DATABASE_ERROR);
        }

        reservationRepository.delete(reservation);
    }

    // 내 예약 목록 보기
    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getMyReservations(User user) {
        List<Reservation> reservations = reservationRepository.findByUserId(user.getId());

        return reservations.stream()
                .map(reservation -> convertToDto(reservation))
                .collect(Collectors.toList());
    }

    // 예약 조회 (단일)
    @Transactional(readOnly = true)
    public ReservationResponseDto getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATABASE_ERROR));

        return convertToDto(reservation);
    }

    // Reservation 엔티티를 ReservationResponseDto로 변환하는 메서드
    private ReservationResponseDto convertToDto(Reservation reservation) {
        ReservationResponseDto responseDto = new ReservationResponseDto();
        responseDto.setId(reservation.getId());
        responseDto.setReservationDate(reservation.getReservationDate());
        responseDto.setCreatedAt(reservation.getCreatedAt());

        // SpaceResponseDto 설정
        SpaceResponseDto spaceDto = new SpaceResponseDto();
        spaceDto.setId(reservation.getSpace().getId());
        spaceDto.setName(reservation.getSpace().getName());
        spaceDto.setLocation(reservation.getSpace().getLocation());
        spaceDto.setDescription(reservation.getSpace().getDescription());
        spaceDto.setPrice(reservation.getSpace().getPrice());
        spaceDto.setStartTime(reservation.getSpace().getStartTime());
        spaceDto.setEndTime(reservation.getSpace().getEndTime());
        spaceDto.setCapacity(reservation.getSpace().getCapacity());

        responseDto.setSpace(spaceDto);  // Space 정보를 포함

        return responseDto;
    }
}
