package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.entity.DonationEntity;
import com.dog.feliz.user.service.entity.FairEntity;
import com.dog.feliz.user.service.entity.SponsorshipEntity;
import com.dog.feliz.user.service.entity.VolunteerEntity;
import com.dog.feliz.user.service.entity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailTemplateService {

    private final TemplateEngine templateEngine;

    @Value("${mail.site.url:http://abrigodogfeliz.qzz.io/}")
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

    public String renderVolunteer(VolunteerEntity volunteer, UserEntity user) {
        Context ctx = new Context();
        ctx.setVariable("volunteer", volunteer);
        ctx.setVariable("user", user);
        ctx.setVariable("siteUrl", siteUrl);
        return templateEngine.process("emails/voluntariados", ctx);
    }

    public String renderFair(FairEntity fair) {
        Context ctx = new Context();
        ctx.setVariable("fair", fair);
        ctx.setVariable("siteUrl", siteUrl);
        return templateEngine.process("emails/feira", ctx);
    }

    public String renderUpdatePassword(String code) {
        Context ctx = new Context();
        ctx.setVariable("code", code);
        return templateEngine.process("emails/update_password", ctx);

    }
}