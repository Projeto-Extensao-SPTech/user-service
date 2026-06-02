package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "fair")
public class FairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fairDate;
    private LocalDateTime fairHour;
    private Integer interest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Column(name = "image_keys", columnDefinition = "TEXT")
    private String imageKeys;

    public List<String> getImageKeys() {
        if (imageKeys == null || imageKeys.isBlank()) return List.of();
        return List.of(imageKeys.split(","));
    }

    public void setImageKeys(List<String> keys) {
        this.imageKeys = String.join(",", keys);
    }
}
