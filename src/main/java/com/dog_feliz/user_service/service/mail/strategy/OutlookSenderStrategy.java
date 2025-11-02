package com.dog_feliz.user_service.service.mail.strategy;

import com.dog_feliz.user_service.controller.dto.EmailRequest;
import com.dog_feliz.user_service.service.mail.MailSenderAvailable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service(value = MailSenderAvailable.OUTLOOK_SENDER)
public class OutlookSenderStrategy implements MailSenderStrategy {

    @Autowired
    @Qualifier("outlookMailSender")
    private JavaMailSender sender;

    @Override
    public void sendSimpleEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getTo());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getContent());
        sender.send(message);
    }

    @Override
    public void sendEmailWithAttachment(EmailRequest emailRequest) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailRequest.getTo());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(emailRequest.getContent());

        // Adjust logic to consider each type of file in extension, example: .png, .pdf, .docx, and others
        FileSystemResource file
                = new FileSystemResource(new File(emailRequest.getAttachment()));
        helper.addAttachment("Request File", file);

        sender.send(message);
    }
}
