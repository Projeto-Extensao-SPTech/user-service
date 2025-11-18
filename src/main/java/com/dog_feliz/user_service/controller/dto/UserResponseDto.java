package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.UserEntity;
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
    private final ZonedDateTime created_at;

    public UserResponseDto(Long id, String name, String document, String phone, AddressResponseDto address, String mailAddress, ZonedDateTime created_at) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.mailAddress = mailAddress;
        this.created_at = created_at;
    }

    public UserResponseDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.document = userEntity.getName();
        this.phone = userEntity.getPhone();
        this.address = new AddressResponseDto(userEntity.getAddress());
        this.mailAddress = userEntity.getMailAddress();
        this.created_at = userEntity.getCreatedAt();
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

    public ZonedDateTime getCreated_at() {
        return created_at;
    }
}
