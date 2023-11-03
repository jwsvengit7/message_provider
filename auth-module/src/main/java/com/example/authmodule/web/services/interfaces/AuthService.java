package com.example.authmodule.web.services.interfaces;

import com.example.authmodule.domain.dto.request.LoginRequest;
import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.LoginResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;

public interface AuthService {
    ApiResponse<String, RegisterResponse> authRegister(RegisterRequest registerRequest);
    ApiResponse<String, LoginResponse> authLogin(LoginRequest loginRequest);

}
