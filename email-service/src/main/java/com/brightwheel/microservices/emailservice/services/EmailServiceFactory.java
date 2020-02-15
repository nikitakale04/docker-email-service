package com.brightwheel.microservices.emailservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceFactory.class);

    @Autowired
    private MailGunEmailServiceImpl mailGunEmailServiceImpl;

    @Autowired
    private SendGridEmailServiceImpl sendGridEmailServiceImpl;

    public EmailService getEmailService(String emailServiceProviderServiceName) {

        LOGGER.info(emailServiceProviderServiceName);

        if ("mailgun".equals(emailServiceProviderServiceName)) {
            LOGGER.info("mailgun service returned");
            return mailGunEmailServiceImpl;
        } else {
            LOGGER.info("sendgrid service returned");
            return sendGridEmailServiceImpl;
        }
    }
}
