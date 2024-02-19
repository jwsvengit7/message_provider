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
    private T message;
    private K payload;
    private String message_response;
    public ApiResponse(K payload,T message){
        this.message=message;
        this.payload=payload;
        this.message_response = "Api recieved";
    }
    public ApiResponse(T message){
        this.message=message;
        this.message_response = "Api recieved";
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message=" + message +
                ", payload=" + payload +
                ", message_response='" + message_response + '\'' +
                '}';
    }
}
