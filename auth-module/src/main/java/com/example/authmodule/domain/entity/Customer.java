package com.example.authmodule.domain.entity;


import com.sms.smscommonsmodule.constant.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER_TB")

public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "CUSTOMER_TB",allocationSize = 1,name = "CUSTOMER_TB")
    private Long id;
    @Column(name = "user_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private Registeration_Type type;
    @Column(name = "user_status",nullable = false)
    private boolean status;
    @Column(name = "full_name",nullable = false)
    private String fullname;
    @Column(name = "user_password",nullable = false)
    private String password;
    @Column(name = "user_email",nullable = false)
    private String email;

    @Column(name = "user_roles",nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles roles;

    public Customer(String email,String password){
        this.email=email;
        this.password=password;
    }
    @Column(name = "user_role")
    @OneToMany(mappedBy = "customer",orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<Role> role;



}
