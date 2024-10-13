package com.oshi.ohsi_back.exception.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import com.oshi.ohsi_back.core.properties.ErrorCode;

@Getter
@Builder
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();  // 오류 발생 시점
    private final int statusCode;                                // HTTP 상태 코드 값
    private final String statusCodeName;                         // HTTP 상태 코드 이름 (예: "BAD_REQUEST")
    private final String code;                                   // 커스텀 오류 코드
    private final String message;                                // 오류 메시지
    private final String runtimeValue;                           // 런타임 시 제공되는 추가 오류 값

    // 오류 응답을 생성하는 정적 메서드
    public static ResponseEntity<ErrorResponse> toResponseEntity(
        ErrorCode errorCode, String runtimeValue
    ) {
        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(ErrorResponse.builder()
                .statusCode(errorCode.getHttpStatus().value())    // 상태 코드 값 설정
                .statusCodeName(errorCode.getHttpStatus().name()) // 상태 코드 이름 설정
                .code(errorCode.getCode())                           // 에러 코드 설정
                .message(errorCode.getMessage())                  // 에러 메시지 설정
                .runtimeValue(runtimeValue)                       // 런타임 값 설정
                .build()
            );
    }
}