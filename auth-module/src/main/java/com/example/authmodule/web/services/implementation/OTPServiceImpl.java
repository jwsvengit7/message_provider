package com.example.authmodule.web.services.implementation;

import com.example.authmodule.domain.repository.CustomerRepository;
import com.example.authmodule.domain.repository.OTPRepository;
import com.sms.smscommonsmodule.constant.*;
import com.sms.smscommonsmodule.dto.request.OTPRequest;
import com.sms.smscommonsmodule.dto.request.ResendOTPRequest;
import com.sms.smscommonsmodule.dto.response.ApiResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.entity.OTP;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import com.example.authmodule.messaging_quee.rabbitmq.queue_pjo.OtpQueue;
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
public class OTPServiceImpl implements OTPService {
    private final OTPRepository otpRepositoryValues;
    private final CustomerRepository customerRepositoryValues;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final RabbitmqService<OtpQueue> rabbitmqService;
    private final ProfileServiceImpl profileService;

    @Override
    public void sendotp_message(Customer customer, String otp){
        saveOtp(customer,otp);
        OtpQueue otpQueue = OtpQueue.builder()
                .otp(otp)
                .email(customer.getEmail())
                .type(customer.getType())
                .build();
        System.out.println( RoutingKey.OTP_ROUTING_KEY.getRoutingKeyName());
        System.out.println(Exchange.OTP_EXCHANGE.name());
        rabbitmqService.sendRabbitmq_message(Exchange.OTP_EXCHANGE.name(), RoutingKey.OTP_ROUTING_KEY.getRoutingKeyName(),otpQueue);
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



    @Override
    public ApiResponse<String, String> verify_otp(OTPRequest otpRequest) {
        Customer customer = customerRepositoryValues.findByEmail(otpRequest.getEmail())
                .orElseThrow(()->new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        OTP optUser = otpRepositoryValues.findByCustomer(customer)
                .orElseThrow(()-> new RuntimeException(OTP_NOT_FOUND));
        if(!isValid(optUser)){
            customer.setStatus(true);
            customerRepositoryValues.save(customer);
            profileService.sendRabbitmqProfile(customer);
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

