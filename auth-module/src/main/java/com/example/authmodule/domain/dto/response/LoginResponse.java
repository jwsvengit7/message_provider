package com.example.authmodule.domain.dto.response;

import com.example.authmodule.domain.constant.Roles;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String message;
    private String email;
    private String accessToken;
    private String refreshAccesstoken;
    private Roles roles;
}
