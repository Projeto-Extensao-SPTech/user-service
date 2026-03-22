package com.dog_feliz.user_service.config;

import com.dog_feliz.user_service.service.EnvironmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    private final EnvironmentService environmentService;

    public MailSenderConfig(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @Bean("gmailMailSender")
    public JavaMailSender gmailMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String GMAIL_PROPERTIES_PREFIX = "mail.gmail";
        mailSender.setHost(environmentService.getProperty(GMAIL_PROPERTIES_PREFIX + ".host", String.class));
        mailSender.setPort(environmentService.getProperty(GMAIL_PROPERTIES_PREFIX + ".port", Integer.class));
        mailSender.setUsername(environmentService.getProperty(GMAIL_PROPERTIES_PREFIX + ".username", String.class));
        mailSender.setPassword(environmentService.getProperty(GMAIL_PROPERTIES_PREFIX + ".password", String.class));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }

    @Bean("outlookMailSender")
    public JavaMailSender outlookMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String OUTLOOK_PROPERTIES_PREFIX = "mail.outlook";
        mailSender.setHost(environmentService.getProperty(OUTLOOK_PROPERTIES_PREFIX + ".host", String.class));
        mailSender.setPort(environmentService.getProperty(OUTLOOK_PROPERTIES_PREFIX + ".port", Integer.class));
        mailSender.setUsername(environmentService.getProperty(OUTLOOK_PROPERTIES_PREFIX + ".username", String.class));
        mailSender.setPassword(environmentService.getProperty(OUTLOOK_PROPERTIES_PREFIX + ".password", String.class));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
