package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.dog_feliz.user_service.service.ValidationService;
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

    @PostMapping
    private ResponseEntity<Void> register(@RequestBody NotificationRequestDto notificationRequest) {
        notificationService.register(notificationRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    private ResponseEntity<NotificationResponseDto> register(@RequestBody NotificationRequestDto notificationRequest) {
        validationService.verifyIsAdminUser();
        return ResponseEntity
                .status(202)
                .body(toResponse(notificationService.register(notificationRequest)));
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/send-today-notifications")
    private void sendTodayNotifications() {
        notificationService.sendTodayNotifications();
    }

}
