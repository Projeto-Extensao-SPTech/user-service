package com.dog_feliz.user_service.service.mail;

import com.dog_feliz.user_service.controller.dto.*;
import com.dog_feliz.user_service.entity.DonationEntity;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailTemplateService {

    private final TemplateEngine templateEngine;

    @Value("${mail.site.url:http://localhost:5173/}")
    private String siteUrl;

    public String renderDonation(DonationEntity donation) {
        Context ctx = new Context();
        ctx.setVariable("donation", donation);
        ctx.setVariable("siteUrl", siteUrl);
        return templateEngine.process("emails/doacao", ctx);
    }

    public String renderSponsorship(SponsorshipEntity sponsorship) {
        Context ctx = new Context();
        ctx.setVariable("sponsorship", sponsorship);
        ctx.setVariable("siteUrl", siteUrl);
        return templateEngine.process("emails/patrocinio", ctx);
    }

    public String renderVolunteer(VolunteerEntity volunteer, UserResponseDto user) {
        Context ctx = new Context();
        ctx.setVariable("volunteer", volunteer);
        ctx.setVariable("user", user);
        ctx.setVariable("siteUrl", siteUrl);
        return templateEngine.process("emails/voluntariados", ctx);
    }

    public String renderBulkNotification(List<MailRequestDto> notifications) {
        Context ctx = new Context();
        ctx.setVariable("notifications", notifications);
        ctx.setVariable("siteUrl", siteUrl);
        return templateEngine.process("emails/recorrencia", ctx);
    }
}