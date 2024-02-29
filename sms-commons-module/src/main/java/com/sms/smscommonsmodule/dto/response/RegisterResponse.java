package com.sms.smscommonsmodule.dto.response;


import com.sms.smscommonsmodule.constant.Roles;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    private String email;
    private String status;
    private String message;
    private Roles roles;

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", roles=" + roles +
                '}';
    }
}
