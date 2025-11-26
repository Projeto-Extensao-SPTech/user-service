package com.dog_feliz.user_service.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CollectionPointRequestDto {

    @NotBlank(message = "Deve existir nome do Ponto de Coleta.")
    @Size(min = 5, max = 100, message = "Deve ter entre 5 e 100 caracteres.")
    private final String name;

    @Valid
    private final AddressRequestDto address;

    public CollectionPointRequestDto(String name, AddressRequestDto address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public AddressRequestDto getAddress() {
        return address;
    }
}
