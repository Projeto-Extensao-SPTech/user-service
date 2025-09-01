package com.dog_feliz.user_service.controller.dto;

import java.time.ZonedDateTime;

public class UserResponseDto {
    private final Integer id;
    private String name;
    private String document;
    private String phone;
    private AddressRequestDto address;
    private String email;
    private ZonedDateTime created_at;

    public UserResponseDto(Integer id, String name, String document, String phone, AddressRequestDto address, String email, ZonedDateTime created_at) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressRequestDto getAddress() {
        return address;
    }

    public void setAddress(AddressRequestDto address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }
}
