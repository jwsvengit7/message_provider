package com.example.authmodule.web.controllers;

import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.CustomerDTO;
import com.example.authmodule.web.services.interfaces.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.authmodule.utils.Constant.BASE_URL;

@RestController
@RequestMapping(BASE_URL+"user")
@RequiredArgsConstructor
@Tag(
        name = "User Service SMS SERVICE ",
        description = "User API For testing userdetails")
public class UserController {
    private final AuthService authService;

    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse<String,CustomerDTO>> getUserById(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(authService.findUserId(userId), HttpStatus.OK);

    }
}
