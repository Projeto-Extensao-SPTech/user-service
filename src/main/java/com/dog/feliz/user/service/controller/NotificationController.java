package com.dog.feliz.user.service.controller;

import com.dog.feliz.user.service.controller.dto.NotificationRequestDto;
import com.dog.feliz.user.service.controller.dto.NotificationResponseDto;
import com.dog.feliz.user.service.controller.dto.NotificationSendRequest;
import com.dog.feliz.user.service.controller.dto.PageResponseDto;
import com.dog.feliz.user.service.service.NotificationService;
import com.dog.feliz.user.service.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public NotificationResponseDto getById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping
    public PageResponseDto<NotificationResponseDto> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return notificationService.getAllNotifications(page, size);
    }
}
