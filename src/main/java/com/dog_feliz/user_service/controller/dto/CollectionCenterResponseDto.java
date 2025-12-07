package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.CollectionCenterEntity;

public class CollectionCenterResponseDto {

    private final Integer id;
    private final String name;
    private final AddressEntity address; // Enviamos o objeto completo do endereço

    // Construtor que converte a Entidade do banco para este DTO
    public CollectionCenterResponseDto(CollectionCenterEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
    }

    // Getters (O Jackson usa isso para criar o JSON)
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressEntity getAddress() {
        return address;
    }
}