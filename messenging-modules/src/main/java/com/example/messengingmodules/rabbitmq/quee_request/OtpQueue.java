package com.example.messengingmodules.rabbitmq.quee_request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OtpQueue {
    private String otp;
    private String email;
    private String type;
}
