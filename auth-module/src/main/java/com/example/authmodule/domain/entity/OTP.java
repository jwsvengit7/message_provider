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
@Table(name = "OTP_TB")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name ="OTP_TB" ,allocationSize = 1,sequenceName = "OTP_TB")

    private Long otp_id;
    private String otp;
    private LocalDateTime expiration;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id" ,nullable = false)
    private Customer customer;

}

