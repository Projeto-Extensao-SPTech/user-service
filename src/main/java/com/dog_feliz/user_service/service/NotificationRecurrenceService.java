package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.entity.notification.NotificationRecurrenceEntity;
import com.dog_feliz.user_service.repository.NotificationRecurrenceRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationRecurrenceService {

    private final NotificationRecurrenceRepository notificationRecurrenceRepository;

    public NotificationRecurrenceService(NotificationRecurrenceRepository notificationRecurrenceRepository) {
        this.notificationRecurrenceRepository = notificationRecurrenceRepository;
    }

    public List<NotificationRecurrenceEntity> register(NotificationEntity notification, LocalDate eventDate, List<Integer> recurrences) {
        Set<LocalDate> recurrencesInDays = buildRecurrenceDates(eventDate, recurrences);
        return recurrencesInDays
                .stream()
                .map(date -> notificationRecurrenceRepository.save(new NotificationRecurrenceEntity(notification, date)))
                .toList();
    }

    public void validateNotificationRecurrence(LocalDate eventDate, List<Integer> recurrences) {
        LocalDate today = LocalDate.now();
        if (eventDate.isBefore(today)) {
            throw new IllegalArgumentException("O dia do evento deve ser posterior ao dia atual!");
        }

        Set<LocalDate> recurrencesInDays = buildRecurrenceDates(eventDate, recurrences);
        if (recurrencesInDays.stream().anyMatch(rec -> rec.isBefore(today))) {
            throw new IllegalArgumentException("A data de recorrência deve ser posterior ao dia atual!");
        }
    }

    private Set<LocalDate> buildRecurrenceDates(LocalDate eventDate, List<Integer> recurrences) {
        Set<LocalDate> dates = new HashSet<>();
        dates.add(eventDate);
        recurrences.forEach(r -> dates.add(getDateFromRecurrence(eventDate, r)));
        return dates;
    }

    private LocalDate getDateFromRecurrence(LocalDate eventDate, Integer recurrence) {
        return eventDate.minusDays(recurrence);
    }
}
