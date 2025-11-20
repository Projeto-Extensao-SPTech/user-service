package com.dog_feliz.user_service.entity.notification;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class NotificationRecurrenceId implements Serializable {

    private Long notificationId;
    private LocalDate recurrence;

    public NotificationRecurrenceId() {}

    public NotificationRecurrenceId(Long notificationId, LocalDate recurrence) {
        this.notificationId = notificationId;
        this.recurrence = recurrence;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public LocalDate getRecurrence() {
        return recurrence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationRecurrenceId)) return false;
        NotificationRecurrenceId that = (NotificationRecurrenceId) o;
        return Objects.equals(notificationId, that.notificationId)
                && Objects.equals(recurrence, that.recurrence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, recurrence);
    }
}
