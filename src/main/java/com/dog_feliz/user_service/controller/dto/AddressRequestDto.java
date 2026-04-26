package com.dog_feliz.user_service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class AddressRequestDto {
    @NotBlank
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "O CEP deve estar no formato 00000-000 ou 00000000")
    private final String zip_code;

    @PositiveOrZero(message = "O número deve ser maior ou igual a zero")
    private final Integer number;

    @NotBlank
    @Size(min = 5, max = 60, message = "O nome da rua deve ter entre 5 e 60 caracteres")
    private final String street;

    @Size(max = 60, message = "O complemento pode ter no máximo 60 caracteres")
    private final String complement;

    @NotBlank
    @Size(min = 2, max = 60, message = "A cidade deve ter entre 2 e 60 caracteres")
    private final String city;

    @NotBlank
    @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 caracteres (UF)")
    private final String state;

    @NotBlank
    @Size(min = 2, max = 60, message = "O país deve ter entre 2 e 60 caracteres")
    private final String country;

    public AddressRequestDto(
            String zipCode,
            Integer number,
            String street,
            String complement,
            String city,
            String state,
            String country
    ) {
        this.zip_code = zipCode;
        this.number = number;
        this.street = street;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getZip_code() {
        return zip_code;
    }

    public Integer getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public String getComplement() {
        return complement;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
}