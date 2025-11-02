package com.dog_feliz.user_service.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequestDto {
    @Size(min = 8, max = 40)
    private final String name;
    @Size(min = 11, max = 11)
    private final String document;
    /**
     * Accepted formats to phone field
     * (11) 91234-5678,
     * 11 91234-5678,
     * 11 1234-5678,
     * (21)1234-5678
     */
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$")
    private final String phone;
    @Valid
    private final AddressRequestDto address;
    @Size(min = 8, max = 100)
    @Email
    private final String email;
    private final String password;

    public UserRequestDto(String name, Integer age, String document, String phone, AddressRequestDto address, AddressRequestDto address1, String email, String password) {
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
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

    public AddressRequestDto getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
