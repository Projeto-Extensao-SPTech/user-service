package com.dog_feliz.user_service.controller.dto.mail;

import java.time.ZonedDateTime;

public class MailResponseDto {
    private final String to;
    private final ZonedDateTime sent_at = ZonedDateTime.now();

    public MailResponseDto(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public ZonedDateTime getSent_at() {
        return sent_at;
    }
}
