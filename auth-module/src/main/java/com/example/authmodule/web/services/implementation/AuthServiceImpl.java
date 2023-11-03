package com.example.authmodule.web.services.implementation;

import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.constant.Roles;
import com.example.authmodule.domain.dto.request.LoginRequest;
import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.CustomerDTO;
import com.example.authmodule.domain.dto.response.LoginResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final CustomerRepositoryValues customerRepositoryValues;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ApiResponse<String, RegisterResponse> authRegister(RegisterRequest registerRequest) {
   boolean customer = customerRepositoryValues.isActive(registerRequest.getEmail());
   if(customer){
       throw new CustomerNotFoundException("USER ALREADY EXIST");
   }
        CustomerDTO saveCustomer = customerDTO(registerRequest);
        sendRabbitmqOtp(saveCustomer);
        return new ApiResponse<>(modelMapper.map(saveCustomer, RegisterResponse.class),Registeration_Type.REGISTERATION_TYPE.name());
    }
    private CustomerDTO customerDTO(RegisterRequest customer){
        return modelMapper.map(customerRepositoryValues.save(Customer.builder()
                .email(customer.getEmail())
                .type(Registeration_Type.REGISTERATION_TYPE)
                .password(passwordEncoder.encode(customer.getPassword()))
                .roles(Roles.STAFF)
                .status(false)
                .build()),CustomerDTO.class);
    }

    private void sendRabbitmqOtp(CustomerDTO customerDTO){
        String otp = OtpUtils.generateOtp();
        OtpQueue otpQueue = OtpQueue.builder()
                .otp(otp)
                .email(customerDTO.getEmail())
                .type(customerDTO.getType())
                .build();
        log.info("{} ",otpQueue);
        rabbitTemplate.convertAndSend(rabbitMQConfig.EXCHANGE_NAME,
                rabbitMQConfig.ROUTING_KEY,
                otpQueue);
    }

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
