package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.user.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequestDto {
    @NotNull(message = "O tipo do usuário é obrigatório.")
    private final UserType type;

    @Size(min = 8, max = 40)
    private final String name;

    @Size(min = 11, max = 14)
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
    @JsonProperty("mail_address")
    private final String mailAddress;

    private final String password;

    public UserRequestDto(UserType type, String name, String document, String phone, AddressRequestDto address, String mailAddress, String password) {
        this.type = type;
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.mailAddress = mailAddress;
        this.password = password;
    }

    public UserType getType() {
        return type;
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

    public String getMailAddress() {
        return mailAddress;
    }

    public String getPassword() {
        return password;
    }
}
