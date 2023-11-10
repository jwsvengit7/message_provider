package com.example.authmodule.web.services.implementation;

import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.constant.Roles;
import com.example.authmodule.domain.dto.request.LoginRequest;
import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.request.ResendOTPRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.CustomerDTO;
import com.example.authmodule.domain.dto.response.LoginResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.entity.OTP;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
import com.example.authmodule.domain.repository.values.interfaces.OTPRepositoryValues;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import com.example.authmodule.messagin_quee.rabbitmq.RabbitMQConfig;
import com.example.authmodule.messagin_quee.rabbitmq.quee_request.OtpQueue;
import com.example.authmodule.security.JwtService;
import com.example.authmodule.utils.OtpUtils;
import com.example.authmodule.web.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
            String jwtToken = jwtService.generateToken(authentication);
            String refreshtoken = jwtService.generateRefreshToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ApiResponse<>(logginResponse(jwtToken,refreshtoken,customer),Registeration_Type.LOGIN_TYPE.name());
        }
        throw  new RuntimeException("Invalid Details");
    }

    private LoginResponse logginResponse(String jwt,String refreshToken,Customer customer){
return LoginResponse.builder()
        .roles(customer.getRoles())
        .accessToken(jwt)
        .refreshAccesstoken(refreshToken)
        .phone(customer.getPhone())
        .email(customer.getEmail())
        .message(Registeration_Type.LOGIN_TYPE.name())
        .build();
    }
}
