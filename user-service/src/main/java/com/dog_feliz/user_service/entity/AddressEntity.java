package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 8, max = 8)
    private String zipCode;

    @PositiveOrZero
    private Integer number;

    @Length(min = 5, max = 40)
    private String street;
}
