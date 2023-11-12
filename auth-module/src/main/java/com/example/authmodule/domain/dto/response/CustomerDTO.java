package com.example.authmodule.domain.dto.response;

import com.example.authmodule.domain.constant.Registeration_Type;
import com.example.authmodule.domain.constant.Roles;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String email;
    private String fullname;
    private Registeration_Type type;
    private Roles roles;
    private boolean status;
}
