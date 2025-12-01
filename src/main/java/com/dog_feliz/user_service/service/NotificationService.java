package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.MailRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.entity.notification.NotificationRecurrenceEntity;
import com.dog_feliz.user_service.repository.FairRepository;
import com.dog_feliz.user_service.repository.NotificationRepository;
import com.dog_feliz.user_service.service.mail.MailSenderAvailable;
import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    private final NotificationRecurrenceService notificationRecurrenceService;

    @Qualifier(MailSenderAvailable.GMAIL_SENDER)
    private final MailSenderStrategy mailSender;

    private final FairRepository fairRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationRecurrenceService notificationRecurrenceService,
            MailSenderStrategy mailSender,
            FairRepository fairRepository
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationRecurrenceService = notificationRecurrenceService;
        this.mailSender = mailSender;
        this.fairRepository = fairRepository;
    }

    @Transactional
    public NotificationEntity register(NotificationRequestDto notificationRequest) {
        notificationRecurrenceService.validateNotificationRecurrence(notificationRequest.getEventDate(), notificationRequest.getRecurrences());

        NotificationEntity notificationEntity;
        Long fairId = notificationRequest.getFairId();

        if (fairId != null) {
            var fair = fairRepository.findById(fairId)
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Fair id not found in notification register"));
            notificationEntity = notificationRepository.save(new NotificationEntity(notificationRequest, fair));
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

    public List<NotificationEntity> getAllNotifications() {
        return notificationRepository.findAll();
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
        // adjust attachment field when is a fair notification to send image
        return new MailRequestDto(
                notification.getNotificationType().getDescription(),
                notification.getMessage(),
                null
        );
    }
}

