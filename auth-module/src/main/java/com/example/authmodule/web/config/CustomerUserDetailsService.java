package com.example.authmodule.web.config;

import com.example.authmodule.domain.entity.Customer;
import com.example.authmodule.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("USER NOT FOUND"));
        return new User(customer.getEmail(),customer.getPassword(),getAuthorities(customer));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Customer user){
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRoles().name()));
    }
}
