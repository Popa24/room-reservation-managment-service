package com.example.demo.emailsender;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailgunJavaMailSender implements MailSender {

    private final String domain;
    private final String apiKey;

    public MailgunJavaMailSender(String domain, String apiKey) {
        this.domain = domain;
        this.apiKey = apiKey;
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        send(new SimpleMailMessage[]{simpleMessage});
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        for (SimpleMailMessage message : simpleMessages) {
            MailgunClient.sendEmail(apiKey, domain, message);
        }
    }
}
