package com.oshi.ohsi_back.domain.kitchen.domain.entity;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.mapping.Set;

import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.AddSpaceRequestDto;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "spaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    // AddSpaceRequestDto를 사용하는 생성자
    public Space(AddSpaceRequestDto dto, User owner) {
        this.owner = owner;
        this.name = dto.getName();
        this.location = dto.getLocation();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.capacity = dto.getCapacity();
    }
}