package com.dog_feliz.user_service.scheduler;

import com.dog_feliz.user_service.service.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {
    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    @Scheduled(cron = "0 0 8 * * *")
//    public void sendDailyNotifications() {
//        notificationService.sendTodayNotifications();
//    }
}
