package com.example.authmodule.web.controllers;

import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;
import com.example.authmodule.web.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthControllers {
    private final AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String,RegisterResponse>> create(@RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(authService.authRegister(registerRequest), HttpStatus.CREATED);
    }
}
