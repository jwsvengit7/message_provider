package com.example.profilemodule.web.service.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "auth-module")
public interface FeinClientService {

    @GetMapping("/auth-module/api/v1/{userId}")
    public String getById(@PathVariable Long userId);
}
