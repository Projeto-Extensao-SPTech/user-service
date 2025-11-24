package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.entity.UserEntity;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class SponsorshipResponseDto {
    private Long id;
    private String type;
    private String department;
    private String description;
    private String recurrence;
    private SponsorInfoDto sponsor;

    public SponsorshipResponseDto(SponsorshipEntity entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.department = entity.getDepartment();
        this.recurrence = entity.getRecurrence() != null ? entity.getRecurrence().toString() : null;
        this.description = entity.getDescription();

        if (entity.getSponsor() != null) {
            UserEntity user = entity.getSponsor();

            this.sponsor = new SponsorInfoDto(
                    user.getId(),
                    user.getName(),
                    user.getMailAddress(),
                    user.getPhone()
            );
        } else {
            this.sponsor = null;
        }
    }

    public SponsorshipResponseDto() {
    }

    // GETTERS E SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public SponsorInfoDto getSponsor() {
        return sponsor;
    }

    public void setSponsor(SponsorInfoDto sponsor) {
        this.sponsor = sponsor;
    }
}
