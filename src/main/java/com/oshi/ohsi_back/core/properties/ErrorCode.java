package com.oshi.ohsi_back.core.properties;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // HTTP STATUS 200
    SUCCESS(HttpStatus.OK, "SU", "Success"),
    
    // HTTP STATUS 400
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VF", "Validation Failed"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "DE", "This Email Already Existed"),
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "DN", "This Name Already Existed"),
    DUPLICATE_OSHI(HttpStatus.BAD_REQUEST, "DO", "This Oshi Already Existed"),
    DUPLICATE_CATEGORY(HttpStatus.BAD_REQUEST, "DC", "This Category Already Existed"),
    DUPLICATE_GOODS(HttpStatus.BAD_REQUEST, "DG", "This Goods Already Existed"),
    NOT_EXISTED_USER(HttpStatus.BAD_REQUEST, "NU", "This User Not Existed"),
    NOT_EXISTED_BOARD(HttpStatus.BAD_REQUEST, "NB", "This Board Not Existed"),
    NOT_EXISTED_SALES(HttpStatus.BAD_REQUEST, "NO","this sales Not Existed"),
    // HTTP STATUS 401
    REFRESH_TOKEN_REQUIRED(HttpStatus.UNAUTHORIZED, "REFRESH_TOKEN_REQUIRED", "Refresh token is required."),
    SECURITY_INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "SECURITY_INVALID_ACCESS_TOKEN", "Invalid access token."),
    EMAIL_NOT_EXTRACTED(HttpStatus.UNAUTHORIZED, "EMAIL_NOT_EXTRACTED", "Unable to extract email from the token."),
    SECURITY_INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "SECURITY_INVALID_REFRESH_TOKEN", "Invalid refresh token."),

    SIGN_IN_FAILED(HttpStatus.UNAUTHORIZED, "SF", "Sign In Failed"),
    AUTHORIZATION_FAIL(HttpStatus.UNAUTHORIZED, "AF", "Authorization Failed"),
    SECURITY_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "SU", "Security Unauthorized"),  // 추가된 부분
    SECURITY_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "IT", "Invalid Token"),  // 추가된 부분
    
    // HTTP STATUS 403
    NO_PERMISSION(HttpStatus.FORBIDDEN, "NP", "Do Not Have Permission"),
    SECURITY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "AD", "Access Denied"), // 추가된 부분

    // HTTP STATUS 404
    NOT_FOUND(HttpStatus.NOT_FOUND, "NF", "Not Found"),
    
    // HTTP STATUS 500
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DBE", "Database Error");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}