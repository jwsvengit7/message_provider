package com.sms.smscommonsmodule.dto.response;

import com.sms.smscommonsmodule.constant.Roles;
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
