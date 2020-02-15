package com.brightwheel.microservices.emailservice.services;

import com.brightwheel.microservices.emailservice.pojos.Email;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${emailServiceProvider.serviceName:}") public String emailServiceProviderServiceName;

    @Autowired
    private EmailServiceFactory emailServiceFactory;

    public void sendSimpleMessage(Email email) throws Exception {
        email.body = html2text(email.body);
        EmailService emailService = emailServiceFactory.getEmailService(emailServiceProviderServiceName);
        emailService.sendSimpleMessage(email);
    }

    private static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
