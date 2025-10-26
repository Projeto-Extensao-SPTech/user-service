package com.dog_feliz.user_service.service.mail.factory;

import com.dog_feliz.user_service.service.mail.strategy.MailSenderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@Service
public class MailSenderFactory {
    @Autowired
    private Map<String, MailSenderStrategy> mailsSenders;

    public Map<String, MailSenderStrategy> getMailsSenders() {
        return mailsSenders;
    }

    public MailSenderStrategy getSender(String senderName) {
        MailSenderStrategy strategy = mailsSenders.get(senderName);
        if (strategy == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "[ERROR] Sender not found for passed send name: " + senderName);
        }
        return strategy;
    }
}
