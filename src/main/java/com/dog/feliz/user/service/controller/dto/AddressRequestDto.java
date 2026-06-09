package com.dog.feliz.user.service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {

    @NotBlank
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "O CEP deve estar no formato 00000-000 ou 00000000")
    private String zipCode;

    @PositiveOrZero(message = "O número deve ser maior ou igual a zero")
    private Integer number;

    @NotBlank
    @Size(min = 5, max = 60, message = "O nome da rua deve ter entre 5 e 60 caracteres")
    private String street;

    @Size(max = 60, message = "O complemento pode ter no máximo 60 caracteres")
    private String complement;

    @NotBlank
    @Size(min = 2, max = 60, message = "A cidade deve ter entre 2 e 60 caracteres")
    private String city;

    @NotBlank
    @Size(min = 2, max = 2, message = "O estado deve ter exatamente 2 caracteres (UF)")
    private String state;

    @NotBlank
    @Size(min = 2, max = 60, message = "O país deve ter entre 2 e 60 caracteres")
    private String country;

    public AddressRequestDto(
            String zipCode,
            Integer number,
            String street,
            String complement,
            String city,
            String state,
            String country
    ) {
        this.zipCode = zipCode;
        this.number = number;
        this.street = street;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public AddressRequestDto() {}
}