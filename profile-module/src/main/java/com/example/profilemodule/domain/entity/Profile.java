package com.example.profilemodule.domain.entity;

import com.example.profilemodule.domain.enums.Roles;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;
@Getter
@Setter
@Document
@Collation(value = "profile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    private String id;
    private String firstName;
    private Long identityNo;
    private String lastName;
    private String email;
    private Roles roles;
    private String dob;
    private Long bvn;
    private List<AccountDetails> accountDetails;

}
