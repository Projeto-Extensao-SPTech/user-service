package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.entity.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "volunteer_tb")
public class VolunteerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDate availableDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    protected VolunteerEntity() {}

    public VolunteerEntity(String message, LocalDate availableDate, UserEntity userEntity) {
        this.message = message;
        this.availableDate = availableDate;
        this.userEntity = userEntity;
    }

    public VolunteerEntity(Long id, String message, LocalDate availableDate, UserEntity userEntity) {
        this.id = id;
        this.message = message;
        this.availableDate = availableDate;
        this.userEntity = userEntity;
    }


    public Long getId() { return id; }
    public String getMessage() { return message; }
    public LocalDate getAvailableDate() { return availableDate; }
    public UserEntity getUserEntity() { return userEntity; }
}

