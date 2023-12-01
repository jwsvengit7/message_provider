package com.example.profilemodule.web.config;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

@LoadBalancerClient(value = "auth-module")
public class LoadBalance {

    @LoadBalanced
    @Bean
    public Feign.Builder builder(){
        return Feign.builder();
    }

}
