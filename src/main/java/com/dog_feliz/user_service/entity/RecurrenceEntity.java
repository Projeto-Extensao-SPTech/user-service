package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "recurrence")
public class RecurrenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    public RecurrenceEntity() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }
}
