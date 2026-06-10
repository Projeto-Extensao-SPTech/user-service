package com.dog.feliz.user.service.shared.utils;

import com.dog.feliz.user.service.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UserTokenValidationUtils {

    private final JwtService jwtService;

    private final HttpServletRequest httpServletRequest;

    public Long getUserId() {
        return jwtService.extractClaim(
                getToken(),
                claims -> claims.get("id", Long.class)
        );
    }

    public UserTokenValidationUtils(JwtService jwtService, HttpServletRequest httpServletRequest) {
        this.jwtService = jwtService;
        this.httpServletRequest = httpServletRequest;
    }

    public Boolean isAdminUser() {
        return jwtService.extractClaim(
                getToken(),
                claims -> claims.get("is_admin", Boolean.class)
        );
    }

    public Boolean isValidUserId(Long userId) {
        return userId.equals(jwtService.extractClaim(
                getToken(),
                claims -> claims.get("id", Long.class)
        ));
    }

    private String getToken() {
        return jwtService.getTokenFromRequestObject(httpServletRequest);
    }
}