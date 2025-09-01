package com.dog_feliz.user_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;

@Entity
@Table(name = "address_tb")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 8, max = 8)
    private String zipCode;

    @PositiveOrZero
    private Integer number;

    @Size(min = 5, max = 40)
    private String street;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private UserEntity user;

    @Column(name = "created_at")
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    public Integer getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Integer getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public UserEntity getUser() {
        return user;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
