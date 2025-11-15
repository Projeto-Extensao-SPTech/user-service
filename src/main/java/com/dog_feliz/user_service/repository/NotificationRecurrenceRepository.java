package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.NotificationRecurrenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRecurrenceRepository extends JpaRepository<NotificationRecurrenceEntity, Long> {
}
