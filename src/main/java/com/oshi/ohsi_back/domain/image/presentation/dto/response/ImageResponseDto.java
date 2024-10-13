package com.oshi.ohsi_back.domain.image.presentation.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.oshi.ohsi_back.core.common.Responsecode;
import com.oshi.ohsi_back.core.common.Responsemessage;
import com.oshi.ohsi_back.core.properties.ResponseDto;
import com.oshi.ohsi_back.domain.image.domain.entity.ImageEntity;

import lombok.Getter;

@Getter
public class ImageResponseDto extends ResponseDto{

    private int imageId;
    private ImageEntity imageEntity;
    private ImageResponseDto(ImageEntity imageEntity){
        super(Responsecode.SUCCESSS, Responsemessage.SUCCESSS);
        this.imageId = imageEntity.getId();  // id
        this.imageEntity = imageEntity;
       
    }
    public static ResponseEntity<ImageResponseDto> success(ImageEntity imageEntity) {
        ImageResponseDto responseBody = new ImageResponseDto(imageEntity);
        return ResponseEntity.status(200).body(responseBody);
    }

    
    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto responsebody = new ResponseDto(Responsecode.NOT_EXISTED_USER,Responsemessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responsebody);
    }
}
