package com.example.authmodule.web.services.implementation;

import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.dto.request.LoginRequest;
import com.example.authmodule.domain.dto.response.ApiResponse;
import com.example.authmodule.domain.dto.response.CustomerDTO;
import com.example.authmodule.domain.dto.response.LoginResponse;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import com.example.authmodule.security.JwtService;
import com.example.authmodule.web.config.annotation.InvokeDomain;
import com.example.authmodule.web.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.example.authmodule.utils.Constant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final CustomerRepositoryValues customerRepositoryValues;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
   // private final com.jwsven.mail_markets.services.impl.MailServiceImpl mailService;

    @Override
    @InvokeDomain
    public ApiResponse<String, LoginResponse> authLogin(LoginRequest loginRequest) {
        Customer customer = customerRepositoryValues.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        if(customerRepositoryValues.isActive(customer.getEmail()) && passwordEncoder.matches(loginRequest.getPassword(),customer.getPassword())){
            Authentication authentication = new UsernamePasswordAuthenticationToken(customer.getEmail(),customer.getPassword());
            String jwtToken = jwtService.generateToken(authentication,customer.getRoles());
            String refreshtoken = jwtService.generateRefreshToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ApiResponse<>(logginResponse(jwtToken,refreshtoken,customer),Registeration_Type.LOGIN_TYPE.name());
        }
        throw  new RuntimeException(INVALID_CREDENTIALS);
    }

    @Override
    public ApiResponse<String, CustomerDTO> findUserId(Long userid) {
        Customer customer = customerRepositoryValues.findByUserId(userid)
                .orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        return new ApiResponse<>(mappCustomertoDto(customer),Registeration_Type.USER_DETAILS.name());
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
@Override
public ApiResponse<String,String> verify_token(String token){
       String username  = jwtService.extractUsername(token);
        return ApiResponse.<String, String>builder()
                .message("Jwt Token")
                .payload(username)
                .message_response(username)
                .build();
    }
    private CustomerDTO mappCustomertoDto(Customer customer){
        return CustomerDTO.builder()
                .id(customer.getId())
                .status(customer.isStatus())
                .type(customer.getType())
                .fullname(customer.getFullname())
                .email(customer.getEmail())
                .roles(customer.getRoles())
                .build();
    }
}
