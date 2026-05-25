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
import com.dog_feliz.user_service.repository.DonationRepository;
import com.dog_feliz.user_service.repository.FairRepository;
import com.dog_feliz.user_service.repository.SponsorshipRepository;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
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

    private final FairRepository fairRepository;
    private final SponsorshipRepository sponsorshipRepository;
    private final DonationRepository donationRepository;
    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationProducer notificationProducer,
            MailTemplateService mailTemplateService,
            FairRepository fairRepository,
            SponsorshipRepository sponsorshipRepository,
            DonationRepository donationRepository,
            VolunteerRepository volunteerRepository,
            UserRepository userRepository
    ) {
        this.notificationProducer = notificationProducer;
        this.mailTemplateService = mailTemplateService;
        this.fairRepository = fairRepository;
        this.sponsorshipRepository = sponsorshipRepository;
        this.donationRepository = donationRepository;
        this.volunteerRepository = volunteerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void schedule(NotificationRequestDto request) {
        Long fairId = request.getFairId();
        if (fairId != null) fairRepository.findById(fairId);

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
        switch (type) {
            case FAIR -> {
                FairEntity fair = fairRepository.findById(referenceId)
                        .orElseThrow(() -> new EntityNotFoundException("Fair not found with id: " + referenceId));
                return mailTemplateService.renderFair(fair);
            }
            case DONATION -> {
                DonationEntity donation = donationRepository.findById(referenceId)
                        .orElseThrow(() -> new EntityNotFoundException("Donation not found with id: " + referenceId));
                return mailTemplateService.renderDonation(donation);
            }
            case VOLUNTEER -> {
                VolunteerEntity volunteer = volunteerRepository.findById(referenceId)
                        .orElseThrow(() -> new EntityNotFoundException("Volunteer not found with id: " + referenceId));
                UserEntity user = userRepository.findById(volunteer.getUserEntity().getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + volunteer.getUserEntity().getId()));
                return mailTemplateService.renderVolunteer(volunteer, user);
            }
            case SPONSORSHIP -> {
                SponsorshipEntity sponsorship = sponsorshipRepository.findById(referenceId)
                        .orElseThrow(() -> new EntityNotFoundException("Sponsorship not found with id: " + referenceId));
                return mailTemplateService.renderSponsorship(sponsorship);
            }
        }
        return null;
    }
}


