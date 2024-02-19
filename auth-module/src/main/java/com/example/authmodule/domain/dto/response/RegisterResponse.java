package com.example.authmodule.domain.dto.response;

import com.example.authmodule.domain.constant.Roles;
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
