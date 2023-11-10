package com.example.authmodule.web.services.interfaces;

import com.example.authmodule.domain.dto.request.ResendOTPRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.entity.Customer;

public interface OTPService {
    ApiResponse<String,String> verify_otp(String otp, String email);
    ApiResponse<String,String> resendOTP(ResendOTPRequest resendOTPRequest);
    void sendotp_message(Customer customer, String otp);
}
