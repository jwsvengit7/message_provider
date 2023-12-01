package com.example.profilemodule.domain.dto;

import com.example.profilemodule.domain.entity.AccountDetails;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
@Getter
@Setter
public class ProfileRequest {
    private String username;
    private String lastname;
    private String firstName;
    private String dob;
    private List<AccountDetails>  accountDetailsList;
    private Long bvn;
}

