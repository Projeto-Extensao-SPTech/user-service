package com.dog_feliz.user_service.controller.dto;

public class AuthorizeRequestDto {
    private String email;
    private String password;

    public AuthorizeRequestDto(String email, String password) {
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
