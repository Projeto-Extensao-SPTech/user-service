package com.dog_feliz.user_service.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailRequest {

    @NotBlank(message = "Recipient email cannot be empty")
    @Email(message = "Invalid email format")
    private final String to;

    @NotBlank(message = "Subject cannot be empty")
    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    private final String subject;

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 5000, message = "Content cannot exceed 5000 characters")
    private final String content;

    @Size(max = 255, message = "Attachment filename cannot exceed 255 characters")
    private final String attachment;

    public EmailRequest(String to, String subject, String content, String attachment) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.attachment = attachment;
    }

    public String getTo() {
        return to;
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
