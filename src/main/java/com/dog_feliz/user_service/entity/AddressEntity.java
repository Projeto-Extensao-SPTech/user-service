package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import com.dog_feliz.user_service.shared.crypto.IntegerCryptoConverter;
import com.dog_feliz.user_service.shared.crypto.StringCryptoConverter;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "address_tb")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringCryptoConverter.class)
    @Column(nullable = false)
    private String zipCode;

    @Convert(converter = StringCryptoConverter.class)
    @Column(nullable = false)
    private String street;

    @Convert(converter = IntegerCryptoConverter.class)
    @Column(nullable = false)
    private Integer number;

    @Convert(converter = StringCryptoConverter.class)
    private String complement;

    @Convert(converter = StringCryptoConverter.class)
    @Column(nullable = false)
    private String city;

    @Convert(converter = StringCryptoConverter.class)
    @Column(nullable = false)
    private String state;

    @Convert(converter = StringCryptoConverter.class)
    @Column(nullable = false)
    private String country;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private UserEntity user = null;

    @Column(name = "created_at", nullable = false, updatable = false)
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    public AddressEntity() {}

    public AddressEntity(AddressRequestDto dto) {
        this.zipCode = dto.getZipCode();
        this.street = dto.getStreet();
        this.number = dto.getNumber();
        this.complement = dto.getComplement();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.country = dto.getCountry();
    }

    public AddressEntity(Long id, AddressRequestDto dto) {
        this(dto);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
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
                ", street='" + street + '\'' +
                ", number=" + number +
                ", complement='" + complement + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setAddress(Long addressId){}
}