package com.dog_feliz.user_service.service.mail.strategy;

import com.dog_feliz.user_service.controller.dto.*;
import com.dog_feliz.user_service.entity.DonationEntity;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.service.mail.MailTemplateService;
import com.dog_feliz.user_service.service.UserService;
import com.dog_feliz.user_service.service.mail.MailSenderAvailable;
import com.dog_feliz.user_service.shared.exception.MailSenderException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    private final JavaMailSender sender;
    private final UserService userService;
    private final MailTemplateService mailTemplateService;

    @Value("${mail.gmail.username}")
    private String defaultMailAddress;

    public GmailSenderStrategy(
            @Qualifier("gmailMailSender") JavaMailSender sender,
            UserService userService,
            MailTemplateService mailTemplateService
    ) {
        this.sender = sender;
        this.userService = userService;
        this.mailTemplateService = mailTemplateService;
    }

    @Override
    public void sendSimpleMail(MailRequestDto mailRequest, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddressTo(to));
        message.setSubject(mailRequest.getSubject());
        message.setText(mailRequest.getContent());
        sender.send(message);
    }

    @Override
    public void sendDonationMail(DonationEntity donation, String to) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(mailAddressTo(to));
        helper.setSubject("Nova Doação - Abrigo Dog Feliz");
        helper.setText(mailTemplateService.renderDonation(donation), true);

        sender.send(message);
    }

    @Override
    public void sendBulkMail(List<MailRequestDto> mailRequests) {
        List<UserEntity> usersForNotification = userService.getUsersForNotification();

        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String[] toAddresses = usersForNotification.stream()
                    .map(UserEntity::getMailAddress)
                    .map(this::mailAddressTo)
                    .toArray(String[]::new);

            helper.setTo(toAddresses);
            helper.setSubject("Notificações do Abrigo Dog Feliz");

            String html = mailTemplateService.renderBulkNotification(mailRequests);
            helper.setText(html, true);

            sender.send(message);
        } catch (MessagingException e) {
            throw new MailSenderException("Error in send bulk mail: " + e.getMessage());
        }
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
    public void sendSponsorshipMail(SponsorshipEntity sponsorship, String to) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(mailAddressTo(to));
        helper.setSubject("Novo Patrocínio - Abrigo Dog Feliz");
        helper.setText(mailTemplateService.renderSponsorship(sponsorship), true);

        sender.send(message);
    }

    @Override
    public void sendVolunteerMail(VolunteerEntity volunteer, UserResponseDto user, String to) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(mailAddressTo(to));
        helper.setSubject("Novo Voluntário - Abrigo Dog Feliz");
        helper.setText(mailTemplateService.renderVolunteer(volunteer, user), true);

        sender.send(message);
    }

    @Override
    public String mailAddressTo(String mailAddress) {
        return mailAddress.equalsIgnoreCase("default") ? defaultMailAddress : mailAddress;
    }
}