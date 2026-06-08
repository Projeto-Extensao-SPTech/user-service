package com.dog.feliz.user.service.entity;

import com.dog.feliz.user.service.controller.dto.DonationRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

@Entity
@Table(name = "donation_tb")
public class DonationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "collection_center_id")
    private Integer collectionCenterId;

    @Column(name = "image_path")
    private String imagePath;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 15, nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer amount;

    @Column(length = 10, nullable = false)
    private String state;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "shipping_method", length = 20)
    private String shippingMethod;

    @Column(name = "created_at", updatable = false)
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    public DonationEntity() {}

    public DonationEntity(DonationRequestDto dto, Long userId, String imagePath) {
        this.userId = userId.intValue();
        this.collectionCenterId = dto.getCollectionCenterId();
        this.name = dto.getName();
        this.type = dto.getType();
        this.amount = dto.getAmount();
        this.state = dto.getState();
        this.description = dto.getDescription();
        this.shippingMethod = dto.getShippingMethod();
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCollectionCenterId() {
        return collectionCenterId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}