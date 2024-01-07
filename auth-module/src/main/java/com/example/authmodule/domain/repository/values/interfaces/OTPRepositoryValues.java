package com.example.authmodule.domain.repository.values.interfaces;

import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.entity.OTP;

import java.util.Optional;

public interface OTPRepositoryValues {

    Optional<OTP> findByCustomer(Customer customer);
}
