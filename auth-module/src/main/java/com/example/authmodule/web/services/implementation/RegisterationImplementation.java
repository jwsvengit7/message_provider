package com.example.authmodule.web.services.implementation;

import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.constant.Roles;
import com.example.authmodule.domain.dto.request.RegisterRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.RegisterResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import com.example.authmodule.utils.OtpUtils;
import com.example.authmodule.web.services.interfaces.OTPService;
import com.example.authmodule.web.services.interfaces.RegisterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.authmodule.utils.Constant.*;

@Service
@RequiredArgsConstructor
public class RegisterationImplementation implements RegisterService {
    private final CustomerRepositoryValues customerRepositoryValues;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final OTPService otpServicel;

    @Override
    public ApiResponse<String, RegisterResponse> authRegister(RegisterRequest registerRequest) {
        if(String.valueOf(registerRequest.getEmail()).isEmpty()){
            throw new CustomerNotFoundException(CUSTOMER_ERROR);
        }
        if(customerRepositoryValues.isActive(registerRequest.getEmail())){
            throw new CustomerNotFoundException(CUSTOMER_ALREADY_EXIST);
        }
        Customer saveCustomer = saveUser(registerRequest);
        String otp = OtpUtils.generateOtp();
        otpServicel.sendotp_message(saveCustomer,otp);
        RegisterResponse save = modelMapper.map(saveCustomer, RegisterResponse.class);
        return new ApiResponse<>(save,Registeration_Type.REGISTERATION_TYPE.name());
    }


    private Customer saveUser(RegisterRequest customer){
        return customerRepositoryValues.save(Customer.builder()
                .email(customer.getEmail())
                .type(Registeration_Type.REGISTERATION_TYPE)
                .password(passwordEncoder.encode(customer.getPassword()))
                .roles(Roles.STAFF)
                .fullname(customer.getFullname())
                .status(false)
                .build());
    }






}
