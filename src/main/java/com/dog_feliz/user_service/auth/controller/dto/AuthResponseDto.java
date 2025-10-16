package com.dog_feliz.user_service.auth.controller.dto;

public class AuthResponseDto {
    private final String token;
    private final Long expires_at;

    public AuthResponseDto(String token, Long expiresAt) {
        this.token = token;
        expires_at = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public Long getExpires_at() {
        return expires_at;
    }
}
