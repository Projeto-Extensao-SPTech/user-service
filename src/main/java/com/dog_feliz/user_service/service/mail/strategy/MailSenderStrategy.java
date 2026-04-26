package com.dog_feliz.user_service.service.mail.strategy;

import com.dog_feliz.user_service.controller.dto.*;
import com.dog_feliz.user_service.entity.DonationEntity;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import jakarta.mail.MessagingException;

import java.util.List;

public interface MailSenderStrategy {
    void sendSimpleMail(MailRequestDto mailRequest, String to);

    void sendMailWithAttachment(MailRequestDto mailRequest, String to) throws MessagingException;

    void sendBulkMail(List<MailRequestDto> mailRequestDto);

    String mailAddressTo(String mailAddress);

    void sendDonationMail(DonationEntity donation, String to) throws MessagingException;

    void sendSponsorshipMail(SponsorshipEntity sponsorship, String to) throws MessagingException;

    void sendVolunteerMail(VolunteerEntity volunteer, UserResponseDto user, String to) throws MessagingException;

}


