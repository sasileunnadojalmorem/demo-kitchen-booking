package com.oshi.ohsi_back.domain.image.exception;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.exception.exceptionclass.CustomException;

public class ImageException extends CustomException {

    public ImageException(ErrorCode errorCode){
        super(errorCode);

    }

    public ImageException(ErrorCode errorCode, String message){
        super(errorCode, message);
    }
    
}
