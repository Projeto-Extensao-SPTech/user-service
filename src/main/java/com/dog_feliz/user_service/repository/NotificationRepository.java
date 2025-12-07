package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query(
            value = """
            SELECT * FROM notification_tb n
            JOIN notification_recurrence_tb nr
            ON n.id = nr.notification_id
            WHERE nr.recurrence = :date
            """,
            nativeQuery = true
    )
    List<NotificationEntity> findByRecurrenceDate(@Param("date") LocalDate date);

    @Query(
            value = """
            SELECT DISTINCT n.* FROM notification_tb n
            JOIN notification_recurrence_tb nr
            ON n.id = nr.notification_id
            WHERE nr.recurrence > :date
            """,
            nativeQuery = true
    )
    List<NotificationEntity> findByRecurrenceDateGreaterThan(@Param("date") LocalDate date);
}
