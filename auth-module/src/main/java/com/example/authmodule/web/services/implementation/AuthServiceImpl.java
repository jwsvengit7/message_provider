package com.example.authmodule.web.services.implementation;

import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.dto.request.LoginRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.LoginResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import com.example.authmodule.security.JwtService;
import com.example.authmodule.web.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final CustomerRepositoryValues customerRepositoryValues;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public ApiResponse<String, LoginResponse> authLogin(LoginRequest loginRequest) {
        Customer customer = customerRepositoryValues.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new CustomerNotFoundException("USER NOT FOUND"));
        if(customerRepositoryValues.isActive(customer.getEmail()) && passwordEncoder.matches(loginRequest.getPassword(),customer.getPassword())){
            Authentication authentication = new UsernamePasswordAuthenticationToken(customer.getEmail(),customer.getPassword());
            String jwtToken = jwtService.generateToken(customer);
            String refreshtoken = jwtService.generateRefreshToken(customer);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ApiResponse<>(logginResponse(jwtToken,refreshtoken,customer),Registeration_Type.LOGIN_TYPE.name());
        }
        throw  new RuntimeException("Invalid Details");
    }

    @Override
    public String findUserId(Long userid) {
        Customer customer = customerRepositoryValues.findByUserId(userid)
                .orElseThrow(()-> new CustomerNotFoundException("CUSTOMER NOT FOUND"));
        return customer.getEmail();
    }

    private LoginResponse logginResponse(String jwt,String refreshToken,Customer customer){
return LoginResponse.builder()
        .roles(customer.getRoles())
        .accessToken(jwt)
        .refreshAccesstoken(refreshToken)
        .email(customer.getEmail())
        .message(Registeration_Type.LOGIN_TYPE.name())
        .build();
    }
}
