package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.VolunteerEntity;
import java.time.LocalDate;

public class VolunteerResponseDto {

    private Long id;
    private String message;
    private LocalDate availableDate;
    private Long userId;

    public VolunteerResponseDto(Long id, String message, LocalDate availableDate, Long userId) {
        this.id = id;
        this.message = message;
        this.availableDate = availableDate;
        this.userId = userId;
    }

    public VolunteerResponseDto(VolunteerEntity entity) {
        this.id = entity.getId();
        this.message = entity.getMessage();
        this.availableDate = entity.getAvailableDate();

        this.userId = entity.getUserEntity().getId();
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public LocalDate getAvailableDate() { return availableDate; }
    public Long getUserId() { return userId; }
}
