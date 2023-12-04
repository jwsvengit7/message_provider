package com.example.authmodule.web.controllers;

import com.example.authmodule.domain.dto.request.*;
import com.example.authmodule.domain.dto.response.*;
import com.example.authmodule.web.services.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.authmodule.utils.Constant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL+"auth")
@Tag(name = "Auth Service",
        description = "Authentication API for registration, login, and OTP operations")
public class AuthControllers {
    private final AuthService authService;
    private final RegisterService registerService;
    private final OTPService otpService;


    @Operation(description = "Create user authentication")
    @PostMapping(REGISTER_URL)
    public ResponseEntity<ApiResponse<String, RegisterResponse>> createUserAuthentication(
            @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(registerService.authRegister(registerRequest), HttpStatus.CREATED);
    }

    @Operation(description = "User authentication")
    @PostMapping(LOGIN_URL)
    public ResponseEntity<ApiResponse<String, LoginResponse>> userAuthentication(
            @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.authLogin(loginRequest), HttpStatus.CREATED);
    }

    @Operation(description = "Resend OTP")
    @PostMapping(RESEND_OTP)
    public ResponseEntity<ApiResponse<String, String>> resendOTP(@RequestBody ResendOTPRequest resendOTPRequest) {
        return new ResponseEntity<>(otpService.resendOTP(resendOTPRequest), HttpStatus.CREATED);
    }

    @Operation(description = "Verify user OTP")
    @GetMapping(VERIFY_OTP)
    public ResponseEntity<ApiResponse<String, String>> verifyUserOTP(
            @RequestBody OTPRequest otpRequest) {
        return new ResponseEntity<>(otpService.verify_otp(otpRequest), HttpStatus.CREATED);
    }

    @GetMapping(VERIFY_TOKEN)
    public ResponseEntity<ApiResponse<String,String>> verify_token(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        return new ResponseEntity<>(authService.verify_token(authorization),HttpStatus.OK);

    }
}
