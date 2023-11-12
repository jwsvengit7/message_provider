package com.example.authmodule.web.services.implementation;

import com.example.authmodule.domain.constant.Exchange;
import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.constant.RoutingKey;
import com.example.authmodule.domain.dto.request.ResendOTPRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.entity.OTP;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
import com.example.authmodule.domain.repository.values.interfaces.OTPRepositoryValues;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import com.example.authmodule.messagin_quee.rabbitmq.RabbitMQConfig;
import com.example.authmodule.messagin_quee.rabbitmq.quee_request.OtpQueue;
import com.example.authmodule.messagin_quee.rabbitmq.quee_request.ProfileRequestQueue;
import com.example.authmodule.utils.OtpUtils;
import com.example.authmodule.web.services.interfaces.OTPService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class OTPServiceImpl implements OTPService {
    private final OTPRepositoryValues otpRepositoryValues;
    private final CustomerRepositoryValues customerRepositoryValues;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
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
        log.info("{} ",otpQueue);
        sendRabbitmq_message(Exchange.OTP_EXCHANGE.name(), RoutingKey.OTP_QUEE.name(),otpQueue);
    }
    private void sendRabbitmqProfile(Customer customer){
        ProfileRequestQueue profileRequestQueue = ProfileRequestQueue.builder()
                .role(customer.getRoles().name())
                .fullname(customer.getFullname())
                .id(customer.getId())
                .email(customer.getEmail())
                .build();

        sendRabbitmq_message(Exchange.PROFILE_ACCESS.name(),RoutingKey.PROFILE_ACCESS.name(), profileRequestQueue);

    }
    @Override
    public ApiResponse<String, String> verify_otp(String otp, String email) {
        Customer customer = customerRepositoryValues.findByEmail(email)
                .orElseThrow(()->new CustomerNotFoundException("USER NOT FOUND"));
        OTP optUser = otpRepositoryValues.findByCustomer(customer)
                .orElseThrow(()-> new RuntimeException("NOT FOUND"));
        if(!isValid(optUser) && optUser!=null){
            customer.setStatus(true);
            customerRepositoryValues.save(customer);
            sendRabbitmqProfile(customer);
            return new ApiResponse<>(customer.getEmail()+" User have been verified",Registeration_Type.OTP_SERVICE.name());
        }else{
            return new ApiResponse<>("Invalid OTP",Registeration_Type.OTP_SERVICE.name());
        }
    }
    private boolean isValid(OTP otpUser){
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime dateCreatd = otpUser.getExpiration();
        Duration duration = Duration.between(dateCreatd,dateNow);
        long elapstTime = duration.toMinutes();
        long minute = 4;
        log.info("elast{} ",elapstTime);
        log.info("minutes{}",minute);
        return   elapstTime > minute;
    }

    @Override
    public ApiResponse<String, String> resendOTP(ResendOTPRequest resendOTPRequest) {
        Customer customer =customerRepositoryValues.findByEmail(resendOTPRequest.getEmail())
                .orElseThrow(()-> new CustomerNotFoundException("CUSTOMER NOT FOUND"));
        sendotp_message(customer, OtpUtils.generateOtp());
        return new ApiResponse<>("OTP Have been sent to your mail", Registeration_Type.OTP_SERVICE.name());
    }
    private void sendRabbitmq_message(String exchange,String routingKey,Object payload){
        System.out.println(payload);
        rabbitTemplate.convertAndSend(exchange,
                routingKey,
                payload);
    }
}

