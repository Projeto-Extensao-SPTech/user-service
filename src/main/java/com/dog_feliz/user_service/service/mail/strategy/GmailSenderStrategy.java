package com.dog_feliz.user_service.service.mail.strategy;

import com.dog_feliz.user_service.controller.dto.MailRequestDto;
import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.service.UserService;
import com.dog_feliz.user_service.service.mail.MailSenderAvailable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service(value = MailSenderAvailable.GMAIL_SENDER)
public class GmailSenderStrategy implements MailSenderStrategy {
    @Autowired
    @Qualifier("gmailMailSender")
    private JavaMailSender sender;

    @Autowired
    private UserService userService;

    @Value("${mail.gmail.username}")
    private String defaultMailAddress;

    @Override
    public void sendSimpleMail(MailRequestDto mailRequest, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddressTo(to));
        message.setSubject(mailRequest.getSubject());
        message.setText(mailRequest.getContent());
        sender.send(message);
    }

    @Override
    public void sendMailWithAttachment(MailRequestDto mailRequest, String to) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(mailAddressTo(to));
        helper.setSubject(mailRequest.getSubject());
        helper.setText(mailRequest.getContent());

        // Adjust logic to consider each type of file in extension, example: .png, .pdf, .docx, and others
        FileSystemResource file
                = new FileSystemResource(new File(mailRequest.getAttachment()));
        helper.addAttachment("Request File", file);

        sender.send(message);
    }

    @Override
    public void sendBulkMail(List<MailRequestDto> mailRequests) {
        List<UserEntity> usersForNotification = userService.getUsersForNotification();
        for (MailRequestDto mailRequest : mailRequests) {
            usersForNotification.forEach(user -> this.sendSimpleMail(mailRequest, user.getMailAddress()));
        }
    }

    @Override
    public String mailAddressTo(String mailAddress) {
        return mailAddress.equalsIgnoreCase("default") ? defaultMailAddress : mailAddress;
    }
}
