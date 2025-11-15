package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.RecurrenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecurrenceRepository extends JpaRepository<RecurrenceEntity, Long> {
}
