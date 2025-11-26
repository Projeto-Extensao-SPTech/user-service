package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.CollectionPointEntity;

import java.time.ZonedDateTime;

public class CollectionPointResponseDto {

    private final Long id;
    private final String name;
    private final AddressResponseDto address;
    private final ZonedDateTime created_at;


    public CollectionPointResponseDto(Long id, String name, AddressResponseDto address, ZonedDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        created_at = createdAt;
    }

    public CollectionPointResponseDto(CollectionPointEntity collectionPoint) {
        this.id = collectionPoint.getId();
        this.name = collectionPoint.getName();
        this.address = new AddressResponseDto(collectionPoint.getAddress());
        this.created_at = collectionPoint.getAddress().getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public AddressResponseDto getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
