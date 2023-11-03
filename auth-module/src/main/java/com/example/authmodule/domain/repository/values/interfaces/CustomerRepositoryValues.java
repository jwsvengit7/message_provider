package com.example.authmodule.domain.repository.values.interfaces;

import com.example.authmodule.domain.constant.Roles;
import com.example.authmodule.domain.entity.Customer;

import java.util.Optional;

public interface CustomerRepositoryValues {
    Optional<Customer> findAllByRoles(Roles roles);
    Optional<Customer> findByEmail(String email);
    Customer save(Customer customer);
    boolean isActive(String customer);
}
