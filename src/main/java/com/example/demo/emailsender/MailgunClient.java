package com.example.demo.emailsender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class MailgunClient {

    private static final String MAILGUN_API_URL = "https://api.mailgun.net/v3/{domain}/messages";

    public static void sendEmail(String apiKey, String domain, SimpleMailMessage message) {
        RestTemplate restTemplate = new RestTemplate();
        String url = MAILGUN_API_URL.replace("{domain}", domain);

        String authHeader = "Basic " + java.util.Base64.getEncoder()
                .encodeToString(("api:" + apiKey).getBytes());

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", authHeader);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        String payload = String.format("from=%s&to=%s&subject=%s&html=%s",
                message.getFrom(),
                String.join(",", Objects.requireNonNull(message.getTo())),
                message.getSubject(),
                message.getText());

        org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<>(payload, headers);

        restTemplate.postForObject(url, request, String.class);
    }

}
