package com.example.demo.emailsender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfiguration {

    @Value("${mailgun.domain}")
    private String mailgunDomain;

    @Value("${mailgun.apikey}")
    private String mailgunApiKey;

    @Bean
    public MailgunJavaMailSender getMailgunJavaMailSender() {
        return new MailgunJavaMailSender(mailgunDomain, mailgunApiKey);
    }
}
