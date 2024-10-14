package com.oshi.ohsi_back.domain.reservation.domain.entity;

import java.security.Timestamp;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.oshi.ohsi_back.domain.kitchen.domain.entity.Space;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
@Table(name = "reservations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"space_id", "reservation_date"})
})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    // Getters and setters
}