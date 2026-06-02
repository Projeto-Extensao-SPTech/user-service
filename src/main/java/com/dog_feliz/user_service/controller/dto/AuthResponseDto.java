package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.entity.user.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponseDto {
    private final Long id;
    private final String token;
    private final String refreshToken;
    private final UserType type;
    private final String name;
    private final String document;
    private final String phone;
    @JsonProperty("mail_address")
    private final String mailAddress;
    @JsonProperty("is_admin")
    private final Boolean isAdmin;
    @JsonProperty("receive_notification")
    private final Boolean receiveNotification;

    public AuthResponseDto(String token, String refreshToken, UserEntity user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.type = user.getType();
        this.name = user.getName();
        this.document = user.getDocument();
        this.phone = user.getPhone();
        this.mailAddress = user.getMailAddress();
        this.id = user.getId();
        this.isAdmin = user.getIsAdmin();
        this.receiveNotification = user.getReceiveNotifications();
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

    public String getToken() {
        return token;
    }

    public Long getId() { return id; }

    public Boolean getIsAdmin() { return isAdmin; }

    public Boolean getReceiveNotification() {
        return receiveNotification;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
