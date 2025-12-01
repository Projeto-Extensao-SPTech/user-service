package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.entity.user.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponseDto {
    private final Long id;
    private final String token;
    private final Long expires_at;
    private final UserType type;
    private final String name;
    private final String document;
    private final String phone;
    private final String mailAddress;
    @JsonProperty("isAdmin")
    private final Boolean isAdmin;

    public AuthResponseDto(Long id, String token, Long expires_at, UserType type, String name, String document, String phone, String mailAddress, Boolean isAdmin) {
        this.token = token;
        this.expires_at = expires_at;
        this.type = type;
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.mailAddress = mailAddress;
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public AuthResponseDto(String token, Long expires_at, UserEntity user) {
        this.token = token;
        this.expires_at = expires_at;
        this.type = user.getType();
        this.name = user.getName();
        this.document = user.getDocument();
        this.phone = user.getPhone();
        this.mailAddress = user.getMailAddress();
        this.id = user.getId();
        this.isAdmin = user.getIsAdmin();
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public UserType getType() {
        return type;
    }

    public Long getExpires_at() {
        return expires_at;
    }

    public String getToken() {
        return token;
    }

    public Long getId() { return id; }

    public Boolean getIsAdmin() { return isAdmin; }
}
