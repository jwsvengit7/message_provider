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
public class Customer  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Registeration_Type type;
    private boolean status;
    private String password;
    private Long phone;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles roles;

}
