package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.shared.utils.MaskUtils;

import java.time.ZonedDateTime;

public class AddressResponseDto {
    private Long id;
    private String zipCode;
    private Integer number;
    private String street;
    private String city;
    private String state;
    private ZonedDateTime createdAt;

    public AddressResponseDto(Long id, String zipCode, Integer number, String street, String city, String state, ZonedDateTime createdAt) {
        this.id = id;
        this.zipCode = zipCode;
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.createdAt = createdAt;
    }

    public AddressResponseDto(AddressEntity address) {
        this.id = address.getId();
        this.zipCode = address.getZipCode();
        this.number = address.getNumber();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
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

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "AddressResponseDto{" +
                ", zipCode='" + zipCode + '\'' +
                ", number=" + number +
                ", street='" + MaskUtils.maskNameField(street) + '\'' +
                ", city='" + MaskUtils.maskNameField(city) + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
