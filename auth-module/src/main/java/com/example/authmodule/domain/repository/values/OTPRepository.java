package com.example.authmodule.domain.repository.values;

import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP,Long> {
    Optional<OTP> findByCustomer(Customer customer);

}
