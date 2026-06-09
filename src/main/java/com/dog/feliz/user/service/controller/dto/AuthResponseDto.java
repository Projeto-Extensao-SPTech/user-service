package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.entity.user.UserEntity;
import com.dog.feliz.user.service.entity.user.UserType;
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

    public String getPhone() {
        return phone;
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

    public Long getId() {
        return id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
