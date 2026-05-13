package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.notification.NotificationType;

public record NotificationSendRequest (
        NotificationType notificationType,
        String recipientMailAddress,
        String message
) {}
