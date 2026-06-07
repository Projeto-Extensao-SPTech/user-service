package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;
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