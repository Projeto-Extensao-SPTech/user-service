package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.entity.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class UserResponseDto {
    private final Long id;

    private final String name;

    private final String document;

    private final String phone;

    private final AddressResponseDto address;

    @JsonProperty("mail_address")
    private final String mailAddress;

    private final ZonedDateTime createdAt;

    public UserResponseDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.document = userEntity.getDocument();
        this.phone = userEntity.getPhone();
        this.address = new AddressResponseDto(userEntity.getAddress());
        this.mailAddress = userEntity.getMailAddress();
        this.createdAt = userEntity.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public String getPhone() {
        return phone;
    }

    public AddressResponseDto getAddress() {
        return address;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
