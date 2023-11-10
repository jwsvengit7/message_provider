package com.example.authmodule.web.controllers;

import com.example.authmodule.domain.dto.request.LoginRequest;
import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.request.ResendOTPRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.LoginResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;
import com.example.authmodule.web.services.interfaces.AuthService;
import com.example.authmodule.web.services.interfaces.OTPService;
import com.example.authmodule.web.services.interfaces.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthControllers {
    private final AuthService authService;
    private final RegisterService registerService;
    private final OTPService otpService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String,RegisterResponse>> create(@RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(registerService.authRegister(registerRequest), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String, LoginResponse>> loggin(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.authLogin(loginRequest), HttpStatus.CREATED);
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<String, String>> loggin(@RequestBody ResendOTPRequest resendOTPRequest){
        return new ResponseEntity<>(otpService.resendOTP(resendOTPRequest), HttpStatus.CREATED);
    }
    @GetMapping("/verify-user-otp")
    public ResponseEntity<ApiResponse<String, String>> loggin(@RequestParam("email") String email,@RequestParam("otp") String otp ){
        return new ResponseEntity<>(otpService.verify_otp(otp,email), HttpStatus.CREATED);
    }
}
