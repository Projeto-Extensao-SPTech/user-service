package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.client.NotificationClient;
import com.dog_feliz.user_service.controller.dto.MailRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import com.dog_feliz.user_service.queue.event.NotificationCreatedEvent;
import com.dog_feliz.user_service.queue.producer.NotificationProducer;
import com.dog_feliz.user_service.repository.FairRepository;
import com.dog_feliz.user_service.service.mail.MailSenderAvailable;
import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;


@Service
public class NotificationService {

    @Qualifier(MailSenderAvailable.GMAIL_SENDER)
    private final MailSenderStrategy mailSender;
    private final FairRepository fairRepository;
    private final NotificationProducer notificationProducer;
    private final NotificationClient notificationClient;

    public NotificationService(FairRepository fairRepository, NotificationProducer notificationProducer, MailSenderStrategy mailSender, NotificationClient notificationClient) {
        this.mailSender = mailSender;
        this.fairRepository = fairRepository;
        this.notificationProducer = notificationProducer;
        this.notificationClient = notificationClient;
    }

    @Transactional
    public void register(NotificationRequestDto notificationRequest) {
        Long fairId = notificationRequest.getFairId();
        if (fairId != null) {
            fairRepository.findById(fairId)
                    .orElseThrow(() -> new HttpClientErrorException(
                            HttpStatus.NOT_FOUND,
                            "Fair id not found in notification register"
                    ));
        }

        // Removemos a lógica de manter a notificação dentro do domínio e enviamos ela somente para o microservice de notificação.
        List<Integer> recurrences = notificationRequest.getRecurrences();
        NotificationCreatedEvent event = new NotificationCreatedEvent(
                notificationRequest.getType().name(),
                fairId,
                notificationRequest.getMessage(),
                notificationRequest.getEventDate(),
                recurrences
        );
        notificationProducer.sendNotification(event);
    }

    @Transactional
    public void sendTodayNotifications() {
        List<NotificationResponseDto> todayNotifications = notificationClient.findByRecurrenceDate(LocalDate.now());
        if (todayNotifications.isEmpty()) return;
        List<MailRequestDto> mailRequests =
                todayNotifications.stream()
                        .map(this::toMailRequest)
                        .toList();
        mailSender.sendBulkMail(mailRequests);
    }


    private MailRequestDto toMailRequest(NotificationResponseDto notification) {
        return new MailRequestDto(
                notification.getType().getDescription(),
                notification.getMessage(),
                null
        );
    }
}


