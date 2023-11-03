package com.example.authmodule.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomExceptionResponse {
    private String message;
    private int statusCode;
    private String dates;
}
