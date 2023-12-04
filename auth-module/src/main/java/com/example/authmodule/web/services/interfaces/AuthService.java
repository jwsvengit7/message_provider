package com.example.authmodule.web.services.interfaces;

import com.example.authmodule.domain.dto.request.LoginRequest;
import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.request.ResendOTPRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.CustomerDTO;
import com.example.authmodule.domain.dto.response.LoginResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;

public interface AuthService {
    ApiResponse<String, LoginResponse> authLogin(LoginRequest loginRequest);
    ApiResponse<String, CustomerDTO> findUserId(Long userid);
    ApiResponse<String,String> verify_token(String token);

}
