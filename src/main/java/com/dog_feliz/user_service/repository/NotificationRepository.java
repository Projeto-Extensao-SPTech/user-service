package com.dog_feliz.user_service.repository;

import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query(
            value = """
            SELECT * FROM
            notification_tb as n
            JOIN notification_recurrence_tb as nr
            ON n.id = nr.notification_id
            WHERE nr.recurrence = CURRENT_DATE
            """,
            nativeQuery = true
    )
    List<NotificationEntity> findTodayNotifications();
}
