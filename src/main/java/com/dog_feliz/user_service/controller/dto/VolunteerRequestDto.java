package com.dog_feliz.user_service.controller.dto;

import java.time.LocalDate;

public class VolunteerRequestDto {

    private String message;
    private LocalDate availableDate;
    private Long addressId;

    public String getMessage() {
        return message;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public Long getAddressId() {
        return addressId;
    }
}
