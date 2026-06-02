package com.dog_feliz.user_service.controller.dto;

public record NotificationSendRequest (
        NotificationType notificationType,
        String recipientMailAddress,
        String message,
        Long referenceId
) {}
