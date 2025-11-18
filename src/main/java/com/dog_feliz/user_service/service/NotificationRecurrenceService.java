package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.entity.notification.NotificationRecurrenceEntity;
import com.dog_feliz.user_service.repository.NotificationRecurrenceRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationRecurrenceService {
    private final NotificationRecurrenceRepository notificationRecurrenceRepository;

    public NotificationRecurrenceService(NotificationRecurrenceRepository notificationRecurrenceRepository) {
        this.notificationRecurrenceRepository = notificationRecurrenceRepository;
    }

    public List<NotificationRecurrenceEntity> register(NotificationEntity notification, LocalDate eventDate, List<Integer> recurrences) {
        List<LocalDate> recurrencesInDays = new ArrayList<>();
        recurrencesInDays.add(eventDate);
        recurrences.forEach(recurrence -> recurrencesInDays.add(getDateFromRecurrence(eventDate, recurrence)));

        return recurrencesInDays
                .stream()
                .map(recurrence -> notificationRecurrenceRepository.save(new NotificationRecurrenceEntity(notification, recurrence)))
                .toList();
    }

    private LocalDate getDateFromRecurrence(LocalDate eventDate, Integer recurrence) {
        return eventDate.minusDays(recurrence);
    }
}
