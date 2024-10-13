package com.oshi.ohsi_back.exception;

import com.oshi.ohsi_back.core.properties.ErrorCode;
import com.oshi.ohsi_back.exception.dto.ErrorResponse;
import com.oshi.ohsi_back.exception.dto.ValidationErrorResponse;
import com.oshi.ohsi_back.exception.exceptionclass.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(
        CustomException e, HttpServletRequest request
    ) {
        return ErrorResponse.toResponseEntity(e.getErrorCode(), e.getRuntimeValue());
    }

    @ExceptionHandler(value = {
        BindException.class,
        MethodArgumentNotValidException.class
    })
    protected ResponseEntity<List<ValidationErrorResponse>> validationException(BindException e,
        HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        List<ValidationErrorResponse> errors = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ValidationErrorResponse error = new ValidationErrorResponse(
                fieldError.getField(),
                fieldError.getDefaultMessage(),
                fieldError.getRejectedValue()
            );
            errors.add(error);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
        Exception e, HttpServletRequest request
    ) {
        return ErrorResponse.toResponseEntity(ErrorCode.DATABASE_ERROR, e.getMessage());
    }
}
