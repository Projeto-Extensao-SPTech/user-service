package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "adoption_fair_tb")
public class AdoptionFairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime date;
    private ZonedDateTime createdAt;
}
