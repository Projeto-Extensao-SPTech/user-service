package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.SponsorEntity;

public class SponsorRequestDto {
    private Long userId;
    private Long addressId;
    private String name;
    private String document;
    private String department;

    public SponsorRequestDto(Long userId, Long addressId, String name, String document, String department) {
        this.userId = userId;
        this.addressId = addressId;
        this.name = name;
        this.document = document;
        this.department = department;
    }

    public SponsorRequestDto() {
    }

    public SponsorRequestDto(SponsorEntity sponsorEntity) {
        if (sponsorEntity == null) return;
        this.userId = sponsorEntity.getUser() != null ? sponsorEntity.getUser().getId() : null;
        this.addressId = sponsorEntity.getAddress() != null ? sponsorEntity.getAddress().getId() : null;
        this.name = sponsorEntity.getName();
        this.document = sponsorEntity.getDocument();
        this.department = sponsorEntity.getDepartment();
    }

    // GETTERS E SETTERS
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

