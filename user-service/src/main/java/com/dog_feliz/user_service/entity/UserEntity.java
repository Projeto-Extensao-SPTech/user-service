package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "user_tb")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 5, max = 40)
    private String name;

    @Column(name = "document")
    @Size(min = 11, max = 11)
    private String document;

    /**
     * Accepted formats to phone field
     * (11) 91234-5678,
     * 11 91234-5678,
     * 11 1234-5678,
     * (21)1234-5678
     */
    @Column(name = "phone")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$")
    private String phone;

    @Column(name = "email")
    @Size(min = 8, max = 100)
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
    private String email;

    // Trabalhar na validação de senha e criptografia posteriormente
    @Column(name = "password")
    @NotNull
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @Column(name = "created_at")
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    public UserEntity(){}

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
