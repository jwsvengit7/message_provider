package com.example.authmodule.web.services.interfaces;

import com.sms.smscommonsmodule.dto.request.RegisterRequest;
import com.sms.smscommonsmodule.dto.response.ApiResponse;
import com.sms.smscommonsmodule.dto.response.RegisterResponse;

public interface RegisterService {
    ApiResponse<String, RegisterResponse> authRegister(RegisterRequest registerRequest);
}
