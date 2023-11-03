package com.example.authmodule.domain.repository.values;

import com.example.authmodule.domain.constant.Roles;
import com.example.authmodule.domain.dto.response.CustomerDTO;
import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.repository.CustomerRepository;
import com.example.authmodule.domain.repository.values.interfaces.CustomerRepositoryValues;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public  class CustomerValues implements CustomerRepositoryValues {
    private final CustomerRepository customerRepository;
    @Override
    public Optional<Customer> findAllByRoles(Roles roles) {
        return customerRepository.findAllByRoles(roles);
    }
    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    @Override
    public boolean isActive(String customer) {
        return customerRepository.existsByEmail(customer);
    }

}
