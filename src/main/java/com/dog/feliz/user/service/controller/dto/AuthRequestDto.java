package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.shared.utils.MaskUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;

public class AuthRequestDto {
    @Email
    @JsonProperty("mail_address")   
    private final String mailAddress;

    private final String password;

    public AuthRequestDto(String mailAddress, String password) {
        this.mailAddress = mailAddress;
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthRequestDto{" +
                "mailAddress='" + MaskUtils.maskMailAddress(mailAddress) + '\'' +
                '}';
    }
}
