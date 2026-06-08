package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.entity.user.UserType;
import com.dog.feliz.user.service.shared.utils.MaskUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
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

    @NotNull
    @Size(min = 8, max = 100)
    @Email
    @JsonProperty("mail_address")
    private final String mailAddress;

    @Size(min = 8, max = 100)
    private final String password;

    @JsonProperty("is_admin")
    private final Boolean isAdmin;

    public UserRequestDto(
            UserType type,
            String name,
            String document,
            String phone,
            AddressRequestDto address,
            String mailAddress,
            String password,
            Boolean isAdmin
    ) {
        this.type = type;
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.mailAddress = mailAddress;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "type=" + type +
                ", name='" + MaskUtils.maskNameField(name) + '\'' +
                ", document='" + MaskUtils.maskDocument(document) + '\'' +
                ", phone='" + MaskUtils.maskPhone(phone) + '\'' +
                ", mailAddress='" + MaskUtils.maskMailAddress(mailAddress) + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
