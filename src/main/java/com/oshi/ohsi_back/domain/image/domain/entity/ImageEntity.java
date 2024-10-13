package com.oshi.ohsi_back.domain.image.domain.entity;

import com.oshi.ohsi_back.domain.image.domain.enums.ImageType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "related_id", nullable = false)
    private int relatedId;

    @Enumerated(EnumType.STRING)
    @Column(name = "related_type", nullable = false)
    private ImageType relatedType;

    // DTO를 사용한 생성자
    public ImageEntity(String url, int relatedId, ImageType relatedType) {
        this.url = url;
        this.relatedId = relatedId;
        this.relatedType = relatedType;
    }
}