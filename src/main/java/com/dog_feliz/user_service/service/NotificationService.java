package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.queue.event.NotificationCreatedEvent;
import com.dog_feliz.user_service.queue.producer.NotificationProducer;
import com.dog_feliz.user_service.repository.FairRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@Service
public class NotificationService {

    private final FairRepository fairRepository;
    private final NotificationProducer notificationProducer;

    public NotificationService(FairRepository fairRepository, NotificationProducer notificationProducer) {
        this.fairRepository = fairRepository;
        this.notificationProducer = notificationProducer;
    }

    @Transactional
    public void register(NotificationRequestDto notificationRequest) {
        Long fairId = notificationRequest.getFairId();
        if (fairId != null) {
            var fair = fairRepository.findById(fairId)
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
}

