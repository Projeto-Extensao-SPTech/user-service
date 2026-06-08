package com.dog.feliz.user.service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Entity
@Table(name = "fair")
@Getter
@Setter
public class FairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fairDate;

    private LocalDateTime fairHour;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Column(name = "image_keys", columnDefinition = "TEXT")
    private String imageKeys;

    public List<String> getImageKeys() {
        if (imageKeys == null || imageKeys.isBlank()) return List.of();
        return Arrays.asList(imageKeys.split(","));
    }

    public void setImageKeys(List<String> keys) {
        this.imageKeys = keys == null ? null : String.join(",", keys);
    }
}