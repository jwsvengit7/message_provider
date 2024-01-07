package com.example.profilemodule.security;

import com.example.profilemodule.domain.enums.Roles;
import com.example.profilemodule.domain.repository.ProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter {
    private final ProfileRepository profileRepository;

    public Boolean filterSecurity(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        log.info("token" ,token);
        Claims claims = Jwts.parser()
                .setSigningKey("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970ARN304N39FR3NRF44")
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        String role = (String) claims.get("role");
        String email = (String) claims.get("email");
        log.info("role" ,role);
        log.info("email" ,email);

        if (Roles.valueOf(role).name().equals(role)) {
            return profileRepository.findByEmail(email).isPresent();
        }
        return false;
    }
}
