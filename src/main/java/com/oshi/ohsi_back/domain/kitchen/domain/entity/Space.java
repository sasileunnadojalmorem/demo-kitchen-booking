package com.oshi.ohsi_back.domain.kitchen.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.oshi.ohsi_back.domain.image.domain.entity.ImageEntity;
import com.oshi.ohsi_back.domain.kitchen.presentation.dto.request.AddSpaceRequestDto;
import com.oshi.ohsi_back.domain.tag.domain.entity.SpaceTag;
import com.oshi.ohsi_back.domain.user.domain.entitiy.User;
import com.oshi.ohsi_back.domain.reservation.domain.entity.Reservation;  // Reservation 임포트

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "spaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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
    private LocalDateTime createdAt;

    // ImageEntity와 1:1 관계 설정
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity image;

    // SpaceTag와 1:N 관계 설정
    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SpaceTag> spaceTags;

    // Reservation과 1:N 관계 설정
    @OneToOne(mappedBy = "space", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Reservation reservations;

    // AddSpaceRequestDto를 사용하는 생성자
    public static Space fromDto(AddSpaceRequestDto dto, User owner, ImageEntity image) {
        return Space.builder()
            .owner(owner)
            .capacity(dto.getCapacity())
            .name(dto.getName())
            .location(dto.getLocation())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .startTime(dto.getStartTime())
            .endTime(dto.getEndTime() != null ? dto.getEndTime() : LocalTime.of(23, 59))
            .image(image)
            .build();
    }

    public static Space fromDto(AddSpaceRequestDto dto, User owner) {
        return Space.builder()
            .owner(owner)
            .capacity(dto.getCapacity())
            .name(dto.getName())
            .location(dto.getLocation())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .startTime(dto.getStartTime())
            .endTime(dto.getEndTime() != null ? dto.getEndTime() : LocalTime.of(23, 59))
            .build();
    }
}