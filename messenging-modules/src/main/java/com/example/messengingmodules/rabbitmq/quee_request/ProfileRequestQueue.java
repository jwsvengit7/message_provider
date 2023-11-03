package com.example.messengingmodules.rabbitmq.quee_request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileRequestQueue {
    private String firstname;
    private String lastname;
    private String email;
}
