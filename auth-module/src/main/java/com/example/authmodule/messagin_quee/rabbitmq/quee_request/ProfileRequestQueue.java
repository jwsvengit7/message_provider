package com.example.authmodule.messagin_quee.rabbitmq.quee_request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileRequestQueue {
    private String fullname;
    private Long id;
    private String role;
    private String email;
}
