package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.service.NotificationService;
import org.springframework.http.HttpStatus;
import com.dog_feliz.user_service.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ValidationService validationService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public NotificationController(NotificationService notificationService, ValidationService validationService) {
        this.notificationService = notificationService;
        this.validationService = validationService;
    }

    @PostMapping
    private ResponseEntity<Void> register(@RequestBody NotificationRequestDto notificationRequest) {
        validationService.verifyIsAdminUser();
        notificationService.register(notificationRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/send-today-notifications")
    private void sendTodayNotifications() {
        notificationService.sendTodayNotifications();
        log.info("[SEND_TODAY_NOTIFICATIONS] Notifications sent successfully");
    }
}
