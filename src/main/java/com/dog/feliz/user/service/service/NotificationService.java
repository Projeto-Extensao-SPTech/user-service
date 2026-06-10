package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.client.NotificationClient;
import com.dog.feliz.user.service.controller.dto.NotificationRequestDto;
import com.dog.feliz.user.service.controller.dto.NotificationResponseDto;
import com.dog.feliz.user.service.controller.dto.NotificationSendRequest;
import com.dog.feliz.user.service.controller.dto.NotificationType;
import com.dog.feliz.user.service.controller.dto.PageResponseDto;
import com.dog.feliz.user.service.entity.DonationEntity;
import com.dog.feliz.user.service.entity.FairEntity;
import com.dog.feliz.user.service.entity.SponsorshipEntity;
import com.dog.feliz.user.service.entity.VolunteerEntity;
import com.dog.feliz.user.service.entity.user.UserEntity;
import com.dog.feliz.user.service.queue.event.NotificationInstantEvent;
import com.dog.feliz.user.service.queue.event.NotificationScheduledEvent;
import com.dog.feliz.user.service.queue.producer.NotificationProducer;
import com.dog.feliz.user.service.repository.DonationRepository;
import com.dog.feliz.user.service.repository.FairRepository;
import com.dog.feliz.user.service.repository.SponsorshipRepository;
import com.dog.feliz.user.service.repository.UserRepository;
import com.dog.feliz.user.service.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Service
public class NotificationService {

    @Value("${notification.default-recipient-mail}")
    private String defaultRecipientMail;

    private final NotificationProducer notificationProducer;

    private final MailTemplateService mailTemplateService;

    private final NotificationClient notificationClient;

    private final FairRepository fairRepository;

    private final SponsorshipRepository sponsorshipRepository;

    private final DonationRepository donationRepository;

    private final VolunteerRepository volunteerRepository;

    private final UserRepository userRepository;

    public NotificationService(
            NotificationProducer notificationProducer,
            MailTemplateService mailTemplateService, NotificationClient notificationClient,
            FairRepository fairRepository,
            SponsorshipRepository sponsorshipRepository,
            DonationRepository donationRepository,
            VolunteerRepository volunteerRepository,
            UserRepository userRepository
    ) {
        this.notificationProducer = notificationProducer;
        this.mailTemplateService = mailTemplateService;
        this.notificationClient = notificationClient;
        this.fairRepository = fairRepository;
        this.sponsorshipRepository = sponsorshipRepository;
        this.donationRepository = donationRepository;
        this.volunteerRepository = volunteerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void schedule(NotificationRequestDto request) {
        Long fairId = request.getFairId();
        if (fairId != null) {
            fairRepository.findById(fairId);
        }

        List<Integer> recurrences = request.getRecurrences();
        NotificationScheduledEvent event = new NotificationScheduledEvent(
                request.getEventId(),
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

    @Cacheable(value = "notifications", key = "'page:' + #page + ':size:' + #size")
    public PageResponseDto<NotificationResponseDto> getAllNotifications(Integer page, Integer size) {
        return notificationClient.getAll(page, size);
    }

    public NotificationResponseDto getNotificationById(Long id) {
        try {
            return notificationClient.getById(id);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(500));
        }
    }

    private String resolveNotificationContent(
            String message,
            NotificationType type,
            Long referenceId
    ) {
        if (message == null && referenceId == null) {
            throw new IllegalArgumentException("The message is required when reference id is null");
        }

        if (type == NotificationType.UPDATE_PASSWORD && message != null) {
            return mailTemplateService.renderUpdatePassword(message);
        }

        if (referenceId != null) {
            return getContentByNotificationType(type, referenceId);
        }

        return message;
    }

    @SuppressWarnings("checkstyle:MethodLength")
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
                UserEntity user = userRepository.findById(donation.getUserId().longValue())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "User not found with id: " + donation.getUserId()));
                return mailTemplateService.renderDonation(donation, user);
            }
            case VOLUNTEER -> {
                VolunteerEntity volunteer = volunteerRepository.findById(referenceId)
                        .orElseThrow(() -> new EntityNotFoundException("Volunteer not found with id: " + referenceId));
                UserEntity user = userRepository.findById(volunteer.getUserEntity().getId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "User not found with id: " +
                                                volunteer.getUserEntity().getId()
                                )
                        );
                return mailTemplateService.renderVolunteer(volunteer, user);
            }
            case SPONSORSHIP -> {
                SponsorshipEntity sponsorship = sponsorshipRepository.findById(referenceId)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Sponsorship not found with id: " + referenceId));

                UserEntity user = userRepository.findById(sponsorship.getSponsor().getId())
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "User not found with id: " +
                                                sponsorship.getSponsor().getId()
                                )
                        );
                return mailTemplateService.renderSponsorship(sponsorship, user);
            }
        }
        return null;
    }
}


