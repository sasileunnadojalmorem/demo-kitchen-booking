package com.oshi.ohsi_back.exception;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.exception.exceptionclass.CustomException;

public class SaleException extends CustomException {

    public SaleException(ErrorCode errorCode) {
        super(errorCode);
    }
    
    public SaleException(ErrorCode errorCode, String runtimeValue) {
        super(errorCode, runtimeValue);
    }
    
}
