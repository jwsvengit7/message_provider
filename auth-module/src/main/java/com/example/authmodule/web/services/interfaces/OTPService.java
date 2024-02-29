package com.example.authmodule.web.services.interfaces;


import com.example.authmodule.domain.entity.Customer;
import com.sms.smscommonsmodule.dto.request.OTPRequest;
import com.sms.smscommonsmodule.dto.request.ResendOTPRequest;
import com.sms.smscommonsmodule.dto.response.ApiResponse;

public interface OTPService {
    ApiResponse<String,String> verify_otp(OTPRequest otpRequest);
    ApiResponse<String,String> resendOTP(ResendOTPRequest resendOTPRequest);
    void sendotp_message(Customer customer, String otp);
}
