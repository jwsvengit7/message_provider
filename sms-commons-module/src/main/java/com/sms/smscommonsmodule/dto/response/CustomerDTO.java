package com.sms.smscommonsmodule.dto.response;


import com.sms.smscommonsmodule.constant.Registeration_Type;
import com.sms.smscommonsmodule.constant.Roles;
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
