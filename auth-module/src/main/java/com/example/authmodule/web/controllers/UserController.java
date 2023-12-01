package com.example.authmodule.web.controllers;

import com.example.authmodule.web.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @GetMapping("{userId}")
    public ResponseEntity<String> getUserById(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(authService.findUserId(userId), HttpStatus.OK);

    }
}
