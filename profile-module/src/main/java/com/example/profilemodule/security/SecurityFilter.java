package com.example.profilemodule.security;

import com.example.profilemodule.domain.enums.Roles;
import com.example.profilemodule.domain.repository.ProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter {
    private final ProfileRepository profileRepository;
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public Boolean filterSecurity(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        log.info("token {} " ,token);
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        String role = (String) claims.get("role");
        String email = (String) claims.get("email");
        log.info("role {} " ,role);
        log.info("email{} " ,email);

        if (Roles.valueOf(role).name().equals(role)) {
            return profileRepository.findByEmail(email).isPresent();
        }
        return false;
    }
}
