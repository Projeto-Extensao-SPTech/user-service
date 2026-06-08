package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.entity.VolunteerEntity;
import java.time.LocalDate;

public class VolunteerResponseDto {

    private Long id;

    private String message;

    private LocalDate availableDate;

    private Long userId;

    public VolunteerResponseDto(VolunteerEntity entity) {
        this.id = entity.getId();
        this.message = entity.getMessage();
        this.availableDate = entity.getAvailableDate();
        this.userId = entity.getUserEntity().getId();
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public Long getUserId() {
        return userId;
    }
}
