package com.example.authmodule.exceptions.handler;

import com.example.authmodule.exceptions.CustomExceptionResponse;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import com.sms.smscommonsmodule.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptions {
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<String, CustomExceptionResponse>> customerException(CustomerNotFoundException e){
        CustomExceptionResponse customExceptionResponse = CustomExceptionResponse.builder()
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .dates("")
                .build();
        ApiResponse<String,CustomExceptionResponse> apiResponse = new ApiResponse<>(customExceptionResponse,"Customer Message");
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
}
