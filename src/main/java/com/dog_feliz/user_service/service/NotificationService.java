package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.notification.NotificationRequestDto;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.entity.notification.NotificationRecurrenceEntity;
import com.dog_feliz.user_service.repository.AdoptionFairRepository;
import com.dog_feliz.user_service.repository.NotificationRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AdoptionFairRepository adoptionFairRepository;
    private final NotificationRecurrenceService notificationRecurrenceService;

    public NotificationService(
            NotificationRepository notificationRepository,
            AdoptionFairRepository adoptionFairRepository,
            NotificationRecurrenceService notificationRecurrenceService, EntityManager entityManager
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

        List<NotificationRecurrenceEntity> notificationRecurrenceEntities = notificationRecurrenceService.register(
                notificationEntity,
                notificationRequest.getEventDate(),
                notificationRequest.getRecurrences()
        );
        return new NotificationEntity(notificationEntity, notificationRecurrenceEntities);
    }

    public List<NotificationEntity> getByRecurrenceDate(LocalDate date) {
        return notificationRepository.findByRecurrenceDate(date);
    }

    public NotificationEntity getById(Long id) {
        Optional<NotificationEntity> notificationEntity = notificationRepository.findById(id);
        if (notificationEntity.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Notification id not found");
        }
        return notificationEntity.get();
    }

    public List<NotificationEntity> getFutureNotifications() {
        return notificationRepository.findByRecurrenceDateGreaterThan(LocalDate.now());
    }

    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }
}

