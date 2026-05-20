package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationSendRequest;
import com.dog_feliz.user_service.controller.dto.NotificationType;
import com.dog_feliz.user_service.entity.DonationEntity;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.queue.event.NotificationInstantEvent;
import com.dog_feliz.user_service.queue.event.NotificationScheduledEvent;
import com.dog_feliz.user_service.queue.producer.NotificationProducer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class NotificationService {
    @Value("${notification.default-recipient-mail}")
    private String defaultRecipientMail;

    private final NotificationProducer notificationProducer;
    private final MailTemplateService mailTemplateService;

    private final FairService fairService;
    private final SponsorshipService sponsorshipService;
    private final DonationService donationService;
    private final VolunteerService volunteerService;
    private final UserService userService;

    public NotificationService(
            NotificationProducer notificationProducer,
            MailTemplateService mailTemplateService,
            FairService fairService,
            SponsorshipService sponsorshipService,
            DonationService donationService,
            VolunteerService volunteerService,
            UserService userService
    ) {
        this.notificationProducer = notificationProducer;
        this.mailTemplateService = mailTemplateService;
        this.fairService = fairService;
        this.sponsorshipService = sponsorshipService;
        this.donationService = donationService;
        this.volunteerService = volunteerService;
        this.userService = userService;
    }

    @Transactional
    public void schedule(NotificationRequestDto request) {
        Long fairId = request.getFairId();
        if (fairId != null) fairService.getFair(fairId);

        List<Integer> recurrences = request.getRecurrences();
        NotificationScheduledEvent event = new NotificationScheduledEvent(
                request.getType().name(),
                fairId,
                request.getMessage(),
                request.getEventDate(),
                recurrences
        );
        notificationProducer.sendNotificationScheduled(event);
    }

    public void send(NotificationSendRequest request) {
        NotificationType type = request.notificationType();
        notificationProducer.sendNotificationInstant(new NotificationInstantEvent(
                type.name(),
                request.recipientMailAddress() == null ? defaultRecipientMail : request.recipientMailAddress(),
                resolveNotificationContent(request.message(), type, request.referenceId())
        ));
    }



    private String resolveNotificationContent(
            String message,
            NotificationType type,
            Long referenceId
    ) {
        if (message == null && referenceId == null) {
            throw new IllegalArgumentException("The message is required when reference id is null");
        }

        if (referenceId != null) {
            return getContentByNotificationType(type, referenceId);
        }

        return message;
    }

    private String getContentByNotificationType(NotificationType type, Long referenceId) {
        return switch (type) {
            case FAIR -> {
                FairEntity fair = fairService.getFair(referenceId);
                yield  mailTemplateService.renderFair(fair);
            }
            case DONATION -> {
                DonationEntity donation = donationService.getDonationById(referenceId);
                yield  mailTemplateService.renderDonation(donation);
            }
            case VOLUNTEER -> {
                VolunteerEntity volunteer = volunteerService.getVolunteerById(referenceId);
                UserEntity user = userService.getUserById(volunteer.getUserEntity().getId());
                yield  mailTemplateService.renderVolunteer(volunteer, user);
            }
            case SPONSORSHIP -> {
                SponsorshipEntity sponsorship = sponsorshipService.getSponsorshipById(referenceId);
                yield  mailTemplateService.renderSponsorship(sponsorship);
            }
            case GENERAL -> null;
        };
    }
}


