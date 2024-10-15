package com.oshi.ohsi_back.domain.image.domain.entity;

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

    // DTO를 사용한 생성자
    public ImageEntity(String url) {
        this.url = url;
    }
}