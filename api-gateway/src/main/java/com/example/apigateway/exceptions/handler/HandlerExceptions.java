package com.example.apigateway.exceptions.handler;



import com.example.apigateway.exceptions.CustomExceptionResponse;
import com.example.apigateway.exceptions.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptions {
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<?> customerException(CustomNotFoundException e){
        CustomExceptionResponse customExceptionResponse = CustomExceptionResponse.builder()
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .dates("")
                .build();

        return new ResponseEntity<>(customExceptionResponse,HttpStatus.NOT_FOUND);
    }
}
