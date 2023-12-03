package com.example.authmodule.web.controllers;

import com.example.authmodule.domain.dto.request.*;
import com.example.authmodule.domain.dto.response.*;
import com.example.authmodule.web.services.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth Service",
        description = "Authentication API for registration, login, and OTP operations")
public class AuthControllers {
    private final AuthService authService;
    private final RegisterService registerService;
    private final OTPService otpService;


    @Operation(description = "Create user authentication")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String, RegisterResponse>> createUserAuthentication(
            @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(registerService.authRegister(registerRequest), HttpStatus.CREATED);
    }

    @Operation(description = "User authentication")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String, LoginResponse>> userAuthentication(
            @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.authLogin(loginRequest), HttpStatus.CREATED);
    }

    @Operation(description = "Resend OTP")
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<String, String>> resendOTP(@RequestBody ResendOTPRequest resendOTPRequest) {
        return new ResponseEntity<>(otpService.resendOTP(resendOTPRequest), HttpStatus.CREATED);
    }

    @Operation(description = "Verify user OTP")
    @GetMapping("/verify-user-otp")
    public ResponseEntity<ApiResponse<String, String>> verifyUserOTP(
            @RequestParam("email") String email, @RequestParam("otp") String otp) {
        return new ResponseEntity<>(otpService.verify_otp(otp, email), HttpStatus.CREATED);
    }
}
