package com.dog_feliz.user_service.controller.dto;

import java.time.ZonedDateTime;

public class EmailResponse {
    private final String to;
    private final ZonedDateTime sent_at = ZonedDateTime.now();

    public EmailResponse(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public ZonedDateTime getSent_at() {
        return sent_at;
    }
}
