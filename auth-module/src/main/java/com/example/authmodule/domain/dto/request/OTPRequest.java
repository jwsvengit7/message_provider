package com.example.authmodule.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPRequest {
    String email;
    String otp;
}
