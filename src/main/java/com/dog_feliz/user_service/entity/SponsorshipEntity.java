package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.SponsorshipRequestDto;
import com.dog_feliz.user_service.entity.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "sponsorship_tb")
public class SponsorshipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sponsor_id", referencedColumnName = "id", nullable = false)
    private UserEntity sponsor;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String department;

    public SponsorshipEntity() {
    }

    public SponsorshipEntity(UserEntity sponsor, SponsorshipRequestDto dto) {
        this.sponsor = sponsor;
        this.type = dto.getType();
        this.description = dto.getDescription();
        this.department = dto.getDepartment();
    }

    // GETTERS E SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getSponsor() {
        return sponsor;
    }

    public void setSponsor(UserEntity sponsor) {
        this.sponsor = sponsor;
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

    public void setDepartment(String department) {
        this.department = department;
    }
}
