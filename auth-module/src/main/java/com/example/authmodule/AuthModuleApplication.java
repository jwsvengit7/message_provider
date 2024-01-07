package com.example.authmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthModuleApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(AuthModuleApplication.class, args);
	}

}
