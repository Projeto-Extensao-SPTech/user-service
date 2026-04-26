package com.dog_feliz.user_service.service.mail;

import com.dog_feliz.user_service.controller.dto.*;
import com.dog_feliz.user_service.entity.DonationEntity;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import com.dog_feliz.user_service.service.UserService;
import com.dog_feliz.user_service.service.mail.factory.MailSenderFactory;
import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import com.dog_feliz.user_service.shared.exception.MailSenderException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSenderFactory mailSenderFactory;
    private final UserService userService;

    @Value("${mail.default-sender}")
    private String defaultSender;

    private MailSenderStrategy sender() {
        return mailSenderFactory.getSender(defaultSender);
    }

    public void notifyDonation(DonationEntity donation) {
        try {
            sender().sendDonationMail(donation, "default");
        } catch (MessagingException e) {
            throw new MailSenderException("Erro ao enviar e-mail de doação: " + e.getMessage());
        }
    }

    public void notifySponsorship(SponsorshipEntity sponsorship) {
        try {
            sender().sendSponsorshipMail(sponsorship, "default");
        } catch (MessagingException e) {
            throw new MailSenderException("Erro ao enviar e-mail de patrocínio: " + e.getMessage());
        }
    }

    public void notifyVolunteer(VolunteerEntity volunteer) {
        try {
            var user = userService.getUserById(volunteer.getUserEntity().getId());
            sender().sendVolunteerMail(volunteer, user, "default");
        } catch (MessagingException e) {
            throw new MailSenderException("Erro ao enviar e-mail de voluntário: " + e.getMessage());
        }
    }

    public void sendBulkNotifications(List<MailRequestDto> notifications) {
        sender().sendBulkMail(notifications);
    }
}