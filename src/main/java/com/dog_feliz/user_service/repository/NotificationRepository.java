package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("""
       SELECT n
       FROM NotificationEntity n
       JOIN n.notificationRecurrence nr
       WHERE nr.id.recurrence = :date
       """)
    List<NotificationEntity> findByRecurrenceDate(@Param("date") LocalDate date);

    @Query("""
       SELECT DISTINCT n
       FROM NotificationEntity n
       JOIN n.notificationRecurrence nr
       WHERE nr.id.recurrence > :date
       """)
    List<NotificationEntity> findByRecurrenceDateGreaterThan(@Param("date") LocalDate date);
}
