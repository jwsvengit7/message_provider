package com.example.authmodule.web.controllers;

import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.constant.Roles;
import com.example.authmodule.domain.dto.request.LoginRequest;
import com.example.authmodule.domain.dto.request.OTPRequest;
import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.security.JwtAuthFilter;
import com.example.authmodule.web.services.interfaces.AuthService;
import com.example.authmodule.web.services.interfaces.OTPService;
import com.example.authmodule.web.services.interfaces.RegisterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthControllers.class )
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtAuthFilter jwtAuthenticationFilter;
    @MockBean
    private AuthService authService;
    @MockBean
    private RegisterService registerService;

    @MockBean
    private OTPService otpService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;
    private RegisterRequest request;
    private LoginRequest loginRequest;
    private RegisterResponse responseFromUser;
    private Customer user;
    private OTPRequest otpValidateRequest;
    @BeforeEach
    public void init(){
        user  = Customer.builder().fullname("jwsven sma")
                .roles(Roles.STAFF)
                .password(passwordEncoder.encode("1234"))
                .email("chiorlujack@gmail.com")
                .status(false)
                .build();
        request =  new RegisterRequest("chiorlujack@gmail.com","12345","jwsven sma");
        loginRequest =  new LoginRequest("chiorlujack@gmail.com","12345");
        responseFromUser = new RegisterResponse(loginRequest.getEmail(),"true","true",Roles.STAFF);
       // otpValidateRequest =new OtpValidateRequest(RandomValues.generateRandom().substring(0,4),loginRequest.getEmail());

    }
    @Test
    void createUserAuthentication() throws Exception {
        given(registerService.authRegister(request)).willAnswer((invocationOnMock -> {
            request =invocationOnMock.getArgument(0);
            return new ApiResponse<>(responseFromUser, Registeration_Type.REGISTERATION_TYPE.name());
        }));
        ResultActions response = mockMvc.perform(post("/api/v1/auth/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        log.info("*************- TEST ***************");
        log.info("{}",CoreMatchers.is(registerService.authRegister(request).getPayload()));
        log.info("*************- TEST ***************");
        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.email", CoreMatchers.is(registerService.authRegister(request).getPayload().getEmail())))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    void userAuthentication() {
    }

    @Test
    void resendOTP() {
    }

    @Test
    void verifyUserOTP() {
    }

    @Test
    void verify_token() {
    }
}