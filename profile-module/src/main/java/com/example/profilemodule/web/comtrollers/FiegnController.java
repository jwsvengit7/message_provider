package com.example.profilemodule.web.comtrollers;

import com.example.profilemodule.web.service.interfaces.FeinClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/feign/")
@RestController
public class FiegnController {
    private final FeinClientService feinClientService;
    @GetMapping("{userId}")
    public ResponseEntity<String> getUserById(@PathVariable("userId") Long userId){

        return new ResponseEntity<>( feinClientService.getById(userId), HttpStatus.OK);

    }
    @GetMapping("get")
    public ResponseEntity<String> get(){

        return new ResponseEntity<>("get", HttpStatus.OK);

    }
}
