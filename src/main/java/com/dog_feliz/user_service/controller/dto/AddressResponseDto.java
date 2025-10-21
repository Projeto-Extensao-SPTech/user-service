package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.AddressEntity;

import java.time.ZonedDateTime;

public class AddressResponseDto {
    private Long id;
    private String zipCode;
    private Integer number;
    private String street;
    private ZonedDateTime createdAt;

    public AddressResponseDto(Long id, String zipCode, Integer number, String street, ZonedDateTime createdAt) {
        this.id = id;
        this.zipCode = zipCode;
        this.number = number;
        this.street = street;
        this.createdAt = createdAt;
    }

    public AddressResponseDto(AddressEntity address) {
        this.id = address.getId();
        this.zipCode = address.getZipCode();
        this.number = address.getNumber();
        this.street = address.getStreet();
        this.createdAt = address.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Integer getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
