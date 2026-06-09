package com.dog.feliz.user.service.controller.dto;

import java.time.ZonedDateTime;

public class MailResponseDto {
    private final String to;

    private final ZonedDateTime sentAt = ZonedDateTime.now();

    public MailResponseDto(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public ZonedDateTime getSentAt() {
        return sentAt;
    }
}
