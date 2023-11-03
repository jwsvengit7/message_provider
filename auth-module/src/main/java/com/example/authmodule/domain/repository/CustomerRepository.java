package com.example.authmodule.domain.repository;

import com.example.authmodule.domain.constant.Roles;
import com.example.authmodule.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findAllByRoles(Roles roles);
    Optional<Customer> findByEmail(String  email);
    boolean existsByEmail(String email);

}
