package com.example.authmodule.web.services.interfaces;

import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;

public interface RegisterService {
    ApiResponse<String, RegisterResponse> authRegister(RegisterRequest registerRequest);
}
