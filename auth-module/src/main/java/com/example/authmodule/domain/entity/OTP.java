package com.example.authmodule.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otp_id;
    private String otp;
    private LocalDateTime expiration;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id" ,nullable = false)
    private Customer customer;

}

