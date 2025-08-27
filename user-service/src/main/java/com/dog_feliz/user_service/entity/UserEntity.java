package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Length(min = 5, max = 40)
    private String name;

    @Column(name = "document")
    @Length(min = 11, max = 11)
    private String document;

    /**
     * Accepted formats to phone field
     * (11) 91234-5678,
     * 11 91234-5678,
     * 11 1234-5678,
     * (21)1234-5678
     */
    @Column(name = "phone")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$\n")
    private String phone;

    @Column(name = "address_id")
    @OneToOne(mappedBy = "address")
    private AddressEntity address;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
