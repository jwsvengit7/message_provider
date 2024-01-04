package com.example.profilemodule.security;

import com.example.profilemodule.domain.enums.Roles;
import com.example.profilemodule.domain.repository.ProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SecurityFilter {
    private final ProfileRepository profileRepository;

    public Boolean filterSecurity(HttpServletRequest httpServletRequest) {
        String role = httpServletRequest.getHeader("userRole");
        System.out.println(role);
        String email = httpServletRequest.getHeader("email");
        if (Roles.valueOf(role).toString().equals(role)) {
            return profileRepository.findByEmail(email).isPresent();
        }
        return false;
    }
}
