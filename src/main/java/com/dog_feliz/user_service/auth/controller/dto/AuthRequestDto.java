package com.dog_feliz.user_service.auth.controller.dto;

import jakarta.validation.constraints.Email;

public class AuthRequestDto {
    @Email
    private final String email;
    private final String password;

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
