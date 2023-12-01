package com.example.profilemodule.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {
    @Id
    private String id;
    private Long accountNumber;
    private String accountName;
    private String bankName;

    public AccountDetails(String accountName, Long accountNumber, String bankName) {
        this.accountName=accountName;
        this.accountNumber=accountNumber;
        this.bankName=bankName;
    }
}