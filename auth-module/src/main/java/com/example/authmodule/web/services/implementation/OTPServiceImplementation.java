package com.example.authmodule.web.services.implementation;

import com.example.authmodule.domain.constant.Exchange;
import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.constant.RoutingKey;
import com.example.authmodule.domain.dto.request.OTPRequest;
import com.example.authmodule.domain.dto.request.ResendOTPRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.entity.OTP;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
import com.example.authmodule.domain.repository.values.interfaces.OTPRepositoryValues;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import com.example.authmodule.messaging_quee.rabbitmq.queue_pjo.OtpQueue;
import com.example.authmodule.messaging_quee.rabbitmq.queue_pjo.ProfileRequestQueue;
import com.example.authmodule.messaging_quee.rabbitmq.sender.RabbitmqService;
import com.example.authmodule.utils.OtpUtils;
import com.example.authmodule.web.services.interfaces.OTPService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.example.authmodule.utils.Constant.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class OTPServiceImplementation implements OTPService {
    private final OTPRepositoryValues otpRepositoryValues;
    private final CustomerRepositoryValues customerRepositoryValues;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final RabbitmqService rabbitmqService;

    @Override
    public void sendotp_message(Customer customer, String otp){
        saveOtp(customer,otp);
        sendRabbitmqOtp(customer,otp);
    }
    private void saveOtp(Customer customer, String otp) {
        OTP saveOtp = otpRepositoryValues.findByCustomer(customer).orElse(null);
        if(saveOtp==null) {
            OTP otpEntity = new OTP();
            otpEntity.setCustomer(customer);
            otpEntity.setOtp(otp);
            otpEntity.setExpiration(LocalDateTime.now().plusMinutes(5));
            otpRepositoryValues.save(otpEntity);
        }else{
            saveOtp.setCustomer(customer);
            saveOtp.setOtp(otp);
            saveOtp.setExpiration(LocalDateTime.now().plusMinutes(5));
            otpRepositoryValues.save(saveOtp);
        }
    }

    private void sendRabbitmqOtp(Customer customer,String otp){
        OtpQueue otpQueue = OtpQueue.builder()
                .otp(otp)
                .email(customer.getEmail())
                .type(customer.getType())
                .build();
        rabbitmqService.sendRabbitmq_message(Exchange.OTP_EXCHANGE.name(), RoutingKey.OTP_QUEE.name(),otpQueue);
    }
    private void sendRabbitmqProfile(Customer customer){
        ProfileRequestQueue profileRequestQueue = ProfileRequestQueue.builder()
                .role(customer.getRoles().name())
                .fullname(customer.getFullname())
                .id(customer.getId())
                .email(customer.getEmail())
                .build();
        rabbitmqService.sendRabbitmq_message(Exchange.PROFILE_ACCESS.name(),RoutingKey.PROFILE_ACCESS.name(), profileRequestQueue);

    }
    @Override
    public ApiResponse<String, String> verify_otp(OTPRequest otpRequest) {
        Customer customer = customerRepositoryValues.findByEmail(otpRequest.getEmail())
                .orElseThrow(()->new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        OTP optUser = otpRepositoryValues.findByCustomer(customer)
                .orElseThrow(()-> new RuntimeException(OTP_NOT_FOUND));
        if(!isValid(optUser)){
            customer.setStatus(true);
            customerRepositoryValues.save(customer);
            sendRabbitmqProfile(customer);
            return new ApiResponse<>(customer.getEmail()+" User have been verified",Registeration_Type.OTP_SERVICE.name());
        }else{
            return new ApiResponse<>(INVLIAD_OTP,Registeration_Type.OTP_SERVICE.name());
        }
    }
    private boolean isValid(OTP otpUser){
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime dateCreatd = otpUser.getExpiration();
        Duration duration = Duration.between(dateCreatd,dateNow);
        long elapstTime = duration.toMinutes();
        long minute = 4;
        log.info("elapstTime{} ",elapstTime);
        log.info("minutes{} ",minute);
        return   elapstTime > minute;
    }

    @Override
    public ApiResponse<String, String> resendOTP(ResendOTPRequest resendOTPRequest) {
        Customer customer =customerRepositoryValues.findByEmail(resendOTPRequest.getEmail())
                .orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        sendotp_message(customer, OtpUtils.generateOtp());
        return new ApiResponse<>(OTP_SENT_TO_MAIL, Registeration_Type.OTP_SERVICE.name());
    }

}

