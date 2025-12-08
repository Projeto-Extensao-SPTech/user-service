package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.DonationEntity;
import java.time.ZonedDateTime;

public class DonationResponseDto {

    private final Integer id;
    private final String name;
    private final String type;
    private final Integer amount;
    private final String state;
    private final String description;
    private final String shippingMethod;
    private final ZonedDateTime createdAt;

    public DonationResponseDto(DonationEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.type = entity.getType();
        this.amount = entity.getAmount();
        this.state = entity.getState();
        this.description = entity.getDescription();
        this.shippingMethod = entity.getShippingMethod();
        this.createdAt = entity.getCreatedAt();
    }


    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public Integer getAmount() { return amount; }
    public String getState() { return state; }
    public String getDescription() { return description; }
    public String getShippingMethod() { return shippingMethod; }
    public ZonedDateTime getCreatedAt() { return createdAt; }
}