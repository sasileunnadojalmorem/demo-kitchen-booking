package com.oshi.ohsi_back.domain.image.presentation.dto.response;

import java.util.List;

import com.oshi.ohsi_back.domain.image.domain.entity.ImageEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageDto {
    private int imageId;
    private String imageUrl;

    @Builder 
    public ImageDto(ImageEntity imageEntity){
        this.imageId = imageEntity.getId();
        this.imageUrl = imageEntity.getUrl();
    }
}
