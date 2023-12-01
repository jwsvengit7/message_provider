package com.example.authmodule.domain.repository.values;

import com.example.authmodule.domain.constant.Roles;
import com.example.authmodule.domain.dto.response.CustomerDTO;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.entity.OTP;
import com.example.authmodule.domain.repository.CustomerRepository;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
import com.example.authmodule.domain.repository.values.interfaces.OTPRepositoryValues;
import com.example.authmodule.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public  class CustomerValues implements CustomerRepositoryValues, OTPRepositoryValues {
    private final CustomerRepository customerRepository;
    private final OTPRepository otpRepository;
    @Override
    public Optional<Customer> findAllByRoles(Roles roles) {
        return customerRepository.findAllByRoles(roles);
    }
    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Optional<Customer> findByUserId(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    @Override
    public boolean isActive(String customer) {
        return customerRepository.existsByEmail(customer);
    }

    @Override
    public OTP save(OTP otp) {
        return otpRepository.save(otp);
    }

    @Override
    public Optional<OTP> findByCustomer(Customer customer) {
        return otpRepository.findByCustomer(customer);
    }
}
