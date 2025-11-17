package com.dog_feliz.user_service.entity;

import com.dog_feliz.user_service.controller.dto.SponsorRequestDto;
import com.dog_feliz.user_service.shared.crypto.StringCryptoConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "sponsor_tb")
public class SponsorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @Convert(converter = StringCryptoConverter.class)
    private String name;

    @Convert(converter = StringCryptoConverter.class)
    private String document;

    @Convert(converter = StringCryptoConverter.class)
    private String department;

    public SponsorEntity() {}

    public SponsorEntity(SponsorRequestDto dto) {
        this.user = new UserEntity();
        this.user.setUser(dto.getUserId());

        this.address = new AddressEntity();
        this.address.setAddress(dto.getAddressId());

        this.name = dto.getName();
        this.document = dto.getDocument();
        this.department = dto.getDepartment();
    }

    public SponsorEntity(SponsorRequestDto dto, Long id){
        this(dto);
        this.id = id;
    }

    // GETTERS e SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
