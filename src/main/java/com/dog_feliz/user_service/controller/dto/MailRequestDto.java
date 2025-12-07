package com.dog_feliz.user_service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MailRequestDto {
    @NotBlank(message = "Subject cannot be empty")
    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    private final String subject;

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 5000, message = "Content cannot exceed 5000 characters")
    private final String content;

    @Size(max = 255, message = "Attachment filename cannot exceed 255 characters")
    private final String attachment;

    public MailRequestDto(String subject, String content, String attachment) {
        this.subject = subject;
        this.content = content;
        this.attachment = attachment;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getAttachment() {
        return attachment;
    }
}
