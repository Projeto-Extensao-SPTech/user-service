package com.dog_feliz.user_service.user.controller.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class AddressRequestDto {
    @Size(min = 8, max = 8)
    private final String zipCode;
    @PositiveOrZero
    private final Integer number;
    @Size(min = 5, max = 40)
    private final String street;

    public AddressRequestDto(String zipCode, Integer number, String street) {
        this.zipCode = zipCode;
        this.number = number;
        this.street = street;
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
}
