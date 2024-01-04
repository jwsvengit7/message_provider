package com.example.authmodule.messaging_quee.rabbitmq.queue_pjo;

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

    @Override
    public String toString() {
        return "OtpQueue{" +
                "otp='" + otp + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
}
