package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;

import javax.naming.Name;
import java.time.LocalDate;

@Entity
@Table(name = "volunteer_tb")
public class VolunteerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDate availableDate;
    @Column(name = "fk_user_volunteer")
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Long fkUserVolunteer;

    public VolunteerEntity() {
    }

    public VolunteerEntity(Long id, String message, LocalDate availableDate, Long fk_user_volunteer) {
        this.id = id;
        this.message = message;
        this.availableDate = availableDate;
        this.fkUserVolunteer = fk_user_volunteer;
    }

    public Long getFk_user_volunteer() {
        return fkUserVolunteer;
    }

    public void setFk_user_volunteer(Long fk_user_volunteer) {
        this.fkUserVolunteer = fk_user_volunteer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }
}
