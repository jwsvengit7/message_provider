package com.example.authmodule.messagin_quee.rabbitmq.quee_request;

import com.example.authmodule.domain.constant.Registeration_Type;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OtpQueue {
    private String otp;
    private String email;
    private Registeration_Type type;
}
