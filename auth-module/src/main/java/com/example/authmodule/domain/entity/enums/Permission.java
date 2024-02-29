package com.example.authmodule.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Permission {
    USER_CREATE_USER("Super user can create a user"),
    USER_DELETE_USER("Super user can delete a user"),
    USER_CAN_BLOCK_USER("Super user can block a user");

    private String description;

}
