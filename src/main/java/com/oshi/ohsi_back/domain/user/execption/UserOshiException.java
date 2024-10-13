package com.oshi.ohsi_back.domain.user.execption;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.exception.exceptionclass.CustomException;

public class UserOshiException extends CustomException {
    public UserOshiException(ErrorCode errorCode){
        super(errorCode);
    }
    public UserOshiException(ErrorCode errorCode, String runtimeValue){
        super(errorCode, runtimeValue);
    }
}
