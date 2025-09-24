package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.converter.crypto.IntegerCryptoConverter;
import com.dog_feliz.user_service.converter.crypto.StringCryptoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user_tb")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringCryptoConverter.class)
    @Size(min = 5, max = 40)
    private String name;

    @Convert(converter = IntegerCryptoConverter.class)
    @Min(value = 14)
    private String age;

    @Convert(converter = StringCryptoConverter.class)
    @Size(min = 11, max = 11)
    private String document;

    /**
     * Accepted formats to phone field
     * (11) 91234-5678,
     * 11 91234-5678,
     * 11 1234-5678,
     * (21)1234-5678
     */
    @Convert(converter = StringCryptoConverter.class)
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$")
    private String phone;

    @Convert(converter = StringCryptoConverter.class)
    @Size(min = 8, max = 100)
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
    private String email;

    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @Column(name = "created_at", updatable = false)
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    public UserEntity() {
    }

    public UserEntity(UserRequestDto userRequestDto, AddressEntity addressEntity) {
        this.name = userRequestDto.getName();
        this.document = userRequestDto.getDocument();
        this.phone = userRequestDto.getPhone();
        this.email = userRequestDto.getEmail();
        this.password = userRequestDto.getPassword();
        this.address = addressEntity;
    }

    public UserEntity(Long id, UserRequestDto userRequestDto, AddressEntity addressEntity) {
        this.id = id;
        this.name = userRequestDto.getName();
        this.document = userRequestDto.getDocument();
        this.phone = userRequestDto.getPhone();
        this.email = userRequestDto.getEmail();
        this.password = userRequestDto.getPassword();
        this.address = addressEntity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public String getPhone() {
        return phone;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
