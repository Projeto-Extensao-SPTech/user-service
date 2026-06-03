package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationSendRequest;
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

    public NotificationController(NotificationService notificationService, ValidationService validationService) {
        this.notificationService = notificationService;
        this.validationService = validationService;
    }

    @PostMapping("/schedule")
    private ResponseEntity<Void> scheduleNotification(@RequestBody NotificationRequestDto notificationRequest) {
        validationService.verifyIsAdminUser();
        notificationService.schedule(notificationRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/send")
    private ResponseEntity<Void> send(@RequestBody NotificationSendRequest notificationRequest) {
        validationService.verifyIsAdminUser();
        notificationService.send(notificationRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
