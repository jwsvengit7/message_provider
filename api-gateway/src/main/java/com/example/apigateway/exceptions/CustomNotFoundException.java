package com.example.apigateway.exceptions;

public class CustomNotFoundException  extends RuntimeException{
   public CustomNotFoundException(String message){
        super(message);
    }
}
