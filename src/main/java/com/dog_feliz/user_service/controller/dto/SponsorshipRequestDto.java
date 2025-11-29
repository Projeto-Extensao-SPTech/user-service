package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.SponsorshipEntity;

import java.time.LocalDateTime;

public class SponsorshipRequestDto {
    private Long sponsorId;
    private String type;
    private String description;
    private String department;

    public SponsorshipRequestDto(Long sponsorId, String type, LocalDateTime recurrence, String description, String context, String department) {
        this.sponsorId = sponsorId;
        this.type = type;
        this.description = description;
        this.department = department;
    }

    public SponsorshipRequestDto(){}

    public SponsorshipRequestDto(SponsorshipEntity sponsorshipEntity){
        if(sponsorshipEntity == null) return;
        this.sponsorId = sponsorshipEntity.getSponsor() != null ? sponsorshipEntity.getSponsor().getId() : null;
        this.type = sponsorshipEntity.getType();
        this.description = sponsorshipEntity.getDescription();
        this.department = sponsorshipEntity.getDepartment();
    }

    // GETTERS E SETTERS
    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setDepartment(String department){
        this.department = department;
    }
}
