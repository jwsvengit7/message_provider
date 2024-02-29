package com.example.authmodule.web.services.interfaces;


import com.sms.smscommonsmodule.dto.response.ApiResponse;
import com.sms.smscommonsmodule.dto.response.CustomerDTO;
import com.sms.smscommonsmodule.dto.response.LoginResponse;
import com.sms.smscommonsmodule.dto.request.LoginRequest;


public interface AuthService {
    ApiResponse<String, LoginResponse> authLogin(LoginRequest loginRequest);
    ApiResponse<String, CustomerDTO> findUserId(Long userid);
    ApiResponse<String,String> verify_token(String token);

}
