package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.notification.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.notification.NotificationResponseDto;
import com.dog_feliz.user_service.repository.NotificationRecurrenceRepository;
import com.dog_feliz.user_service.repository.NotificationRepository;
import com.dog_feliz.user_service.repository.RecurrenceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private RecurrenceRepository recurrenceRepository;
    private NotificationRecurrenceRepository notificationRecurrenceRepository;

    public NotificationService(NotificationRepository notificationRepository, RecurrenceRepository recurrenceRepository, NotificationRecurrenceRepository notificationRecurrenceRepository) {
        this.notificationRepository = notificationRepository;
        this.recurrenceRepository = recurrenceRepository;
        this.notificationRecurrenceRepository = notificationRecurrenceRepository;
    }

    public NotificationResponseDto register(NotificationRequestDto notificationRequest) {
        return new NotificationResponseDto();
    }

    public List<NotificationResponseDto> getTodayNotifications() {
        return new ArrayList<>();
    }
}
