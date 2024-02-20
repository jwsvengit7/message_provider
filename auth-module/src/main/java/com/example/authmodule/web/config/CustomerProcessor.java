package com.example.authmodule.web.config;


import com.example.authmodule.domain.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer)  {
        return customer;
    }
}