package com.oshi.ohsi_back.domain.reservation.infrastructure;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oshi.ohsi_back.domain.reservation.domain.entity.Reservation;
import com.oshi.ohsi_back.domain.kitchen.domain.entity.Space;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 특정 공간과 날짜에 대한 예약 조회
    List<Reservation> findBySpaceAndReservationDate(Space space, LocalDate reservationDate);

    // 특정 사용자의 예약 목록 조회
    List<Reservation> findByUserId(Long userId);
}
