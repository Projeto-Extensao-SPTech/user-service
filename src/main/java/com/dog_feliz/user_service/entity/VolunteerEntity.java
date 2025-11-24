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
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity address;

    public VolunteerEntity() {
    }

    public VolunteerEntity(Long id, String message, LocalDate availableDate, AddressEntity address) {
        this.id = id;
        this.message = message;
        this.availableDate = availableDate;
        this.address = address;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
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
