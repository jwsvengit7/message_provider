package com.example.authmodule.domain.repository;


import com.example.authmodule.domain.entity.Customer;
import com.sms.smscommonsmodule.constant.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findAllByRoles(Roles roles);
    Optional<Customer> findByEmail(String  email);
    boolean existsByEmail(String email);




}
