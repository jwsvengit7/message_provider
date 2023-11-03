package com.example.authmodule.domain.dto.response;

import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T,K> {
    private T data;
    private K dataK;
    private String message;
    public ApiResponse(K dataK,T data){
        this.data=data;
        this.dataK=dataK;
        this.message = "Api recieved";
    }
    public ApiResponse(T data){
        this.data=data;
        this.message = "Api recieved";
    }
}
