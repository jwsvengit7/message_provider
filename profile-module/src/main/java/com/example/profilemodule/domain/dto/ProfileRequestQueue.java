package com.example.profilemodule.domain.dto;

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
