package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import com.dog_feliz.user_service.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "address_tb")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer number;

    private String complement;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private UserEntity user = null;

    @Column(name = "created_at", nullable = false, updatable = false)
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    public AddressEntity() {}

    public AddressEntity(AddressRequestDto dto) {
        this.zipCode = dto.getZip_code();
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