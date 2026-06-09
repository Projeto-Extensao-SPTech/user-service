package com.dog.feliz.user.service.controller.dto;

public record NotificationSendRequest (
        NotificationType notificationType,
        String recipientMailAddress,
        String message,
        Long referenceId
) {}
