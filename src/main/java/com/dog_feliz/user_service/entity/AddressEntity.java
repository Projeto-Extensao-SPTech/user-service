package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import com.dog_feliz.user_service.converter.crypto.IntegerCryptoConverter;
import com.dog_feliz.user_service.converter.crypto.StringCryptoConverter;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "address_tb")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringCryptoConverter.class)
    private String zipCode;

    @Convert(converter = IntegerCryptoConverter.class)
    private Integer number;

    @Convert(converter = StringCryptoConverter.class)
    private String street;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private UserEntity user = null;

    @Column(name = "created_at")
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    public AddressEntity() {}

    public AddressEntity(AddressRequestDto addressRequestDto) {
        this.number = addressRequestDto.getNumber();
        this.street = addressRequestDto.getStreet();
        this.zipCode = addressRequestDto.getZipCode();
    }

    public AddressEntity(Long id, AddressRequestDto addressRequestDto) {
        this.id = id;
        this.number = addressRequestDto.getNumber();
        this.street = addressRequestDto.getStreet();
        this.zipCode = addressRequestDto.getZipCode();
    }

    public Long getId() {
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

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", zipCode='" + zipCode + '\'' +
                ", number=" + number +
                ", street='" + street + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
