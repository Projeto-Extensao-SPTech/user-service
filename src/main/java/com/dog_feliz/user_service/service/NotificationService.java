package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.MailRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.entity.notification.NotificationRecurrenceEntity;
import com.dog_feliz.user_service.repository.AdoptionFairRepository;
import com.dog_feliz.user_service.repository.NotificationRepository;
import com.dog_feliz.user_service.service.mail.MailSenderAvailable;
import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier(MailSenderAvailable.GMAIL_SENDER)
    private final MailSenderStrategy mailSender;

    public NotificationService(
            NotificationRepository notificationRepository,
            AdoptionFairRepository adoptionFairRepository,
            NotificationRecurrenceService notificationRecurrenceService, MailSenderStrategy mailSender
    ) {
        this.notificationRepository = notificationRepository;
        this.adoptionFairRepository = adoptionFairRepository;
        this.notificationRecurrenceService = notificationRecurrenceService;
        this.mailSender = mailSender;
    }

    @Transactional
    public NotificationEntity register(NotificationRequestDto notificationRequest) {
        notificationRecurrenceService.validateNotificationRecurrence(notificationRequest.getEventDate(), notificationRequest.getRecurrences());

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

    public void sendTodayNotifications() {
        List<NotificationEntity> todayNotifications = this.getByRecurrenceDate(LocalDate.now());
        List<MailRequestDto> mailRequests = todayNotifications.stream().map(notification -> toMailRequest(notification)).toList();
        mailSender.sendBulkMail(mailRequests);
    }

    private MailRequestDto toMailRequest(NotificationEntity notification) {
        // adjust attachment field when is an adoption fair notification to send image
        return new MailRequestDto(
                notification.getNotificationType().getDescription(),
                notification.getMessage(),
                null
        );
    }
}

