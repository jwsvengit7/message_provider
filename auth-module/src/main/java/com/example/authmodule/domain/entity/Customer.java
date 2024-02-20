package com.example.authmodule.domain.entity;

import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.constant.Roles;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_table")

public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "CUSTOMER_TB",allocationSize = 1,initialValue = 1, name = "CUSTOMER_TB")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Registeration_Type type;
    private boolean status;
    private String fullname;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    public Customer(String email,String password){
        this.email=email;
        this.password=password;
    }



}
