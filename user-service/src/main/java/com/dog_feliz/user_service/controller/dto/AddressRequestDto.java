package com.dog_feliz.user_service.controller.dto;

public class AddressRequestDto {
    private String zipCode;
    private Integer number;
    private String street;

    public AddressRequestDto(Integer id, String zipCode, Integer number, String street) {
        this.zipCode = zipCode;
        this.number = number;
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
