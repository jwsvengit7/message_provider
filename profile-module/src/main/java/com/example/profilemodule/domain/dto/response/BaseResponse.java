package com.example.profilemodule.domain.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BaseResponse<T> {
    private String message;
    private T data;
    private Integer statusCode;
    public BaseResponse(T data, Integer code){
        this.message = "resolve endpoints";
        this.statusCode=code;
        this.data=data;
    }

}
