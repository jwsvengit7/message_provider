package com.example.authmodule.messaging_quee.rabbitmq.queue_pjo;

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

    @Override
    public String toString() {
        return "Profile Message{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role  + '\'' +
                ", id="+id+  '\'' +
                '}';
    }
}
