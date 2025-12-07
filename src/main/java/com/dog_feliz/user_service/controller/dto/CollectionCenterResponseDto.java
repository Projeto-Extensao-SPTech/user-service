package com.dog_feliz.user_service.controller.dto;


import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.CollectionCenterEntity;

public class CollectionCenterResponseDto {

    private final Integer id;
    private final String name;
    private final AddressEntity address; // Mandamos o objeto endereço completo

    public CollectionCenterResponseDto(CollectionCenterEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
    }

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