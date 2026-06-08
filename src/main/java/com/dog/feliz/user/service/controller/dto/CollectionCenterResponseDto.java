package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.entity.CollectionCenterEntity;

public class CollectionCenterResponseDto {

    private final Integer id;

    private final String name;

    private final AddressResponseDto address; 

    public CollectionCenterResponseDto(CollectionCenterEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = new AddressResponseDto(entity.getAddress());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressResponseDto getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "CollectionCenterResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}