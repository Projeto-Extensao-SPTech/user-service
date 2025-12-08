package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.DonationRequestDto;
import com.dog_feliz.user_service.controller.dto.DonationResponseDto;
import jakarta.persistence.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "donation_tb")
public class DonationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId; // Seu banco pede INTEGER, embora UserEntity seja Long

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

    // Construtor vazio (Obrigatório JPA)
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

        this.imagePath = imagePath; // Recebe o caminho da única imagem
    }


    // Getters (apenas leitura para resposta da API, se precisar de Setters adicione depois)
    public Integer getId() { return id; }
    public Integer getUserId() { return userId; }
    public Integer getCollectionCenterId() { return collectionCenterId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public Integer getAmount() { return amount; }
    public String getState() { return state; }
    public String getDescription() { return description; }
    public String getShippingMethod() { return shippingMethod; }
    public ZonedDateTime getCreatedAt() { return createdAt; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

}