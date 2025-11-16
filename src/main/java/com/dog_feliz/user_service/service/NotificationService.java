package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.notification.NotificationRequestDto;
import com.dog_feliz.user_service.entity.AdoptionFairEntity;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.repository.AdoptionFairRepository;
import com.dog_feliz.user_service.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AdoptionFairRepository adoptionFairRepository;
    private final NotificationRecurrenceService notificationRecurrenceService;

    public NotificationService(
            NotificationRepository notificationRepository,
            AdoptionFairRepository adoptionFairRepository,
            NotificationRecurrenceService notificationRecurrenceService
    ) {
        this.notificationRepository = notificationRepository;
        this.adoptionFairRepository = adoptionFairRepository;
        this.notificationRecurrenceService = notificationRecurrenceService;
    }

    @Transactional
    public NotificationEntity register(NotificationRequestDto notificationRequest) {
        NotificationEntity notificationEntity;
        Long adoptionFairId = notificationRequest.getAdoptionFairId();

        if (adoptionFairId != null) {
            var adoptionFair = adoptionFairRepository.findById(adoptionFairId)
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Adoption fair id not found in notification register"));
            notificationEntity = notificationRepository.save(new NotificationEntity(notificationRequest, adoptionFair));
        } else {
            notificationEntity = notificationRepository.save(new NotificationEntity(notificationRequest));
        }

        notificationRecurrenceService.register(
                notificationEntity,
                notificationRequest.getEventDate(),
                notificationRequest.getRecurrences()
        );
        return notificationEntity;
    }

    public List<NotificationEntity> getTodayNotifications() {
        return notificationRepository.findTodayNotifications();
    }
}

