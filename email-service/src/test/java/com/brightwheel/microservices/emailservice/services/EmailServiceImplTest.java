package com.brightwheel.microservices.emailservice.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.brightwheel.microservices.emailservice.pojos.Email;


@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    MailGunEmailServiceImpl mailGunEmailService;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    SendGridEmailServiceImpl sendGridEmailService;
    @InjectMocks
    EmailServiceFactory emailServiceFactory;
    @InjectMocks
    EmailServiceImpl emailServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendSimpleMessage() throws Exception {
        EmailService emailService = emailServiceFactory.getEmailService("mailgun");
        Email email = new Email();
        emailService.sendSimpleMessage(email);
        Assert.assertTrue(emailService instanceof MailGunEmailServiceImpl);
        verify(mailGunEmailService, times(1)).sendSimpleMessage(any());

        emailService = emailServiceFactory.getEmailService("sendGrid");
        emailService.sendSimpleMessage(email);
        Assert.assertTrue(emailService instanceof SendGridEmailServiceImpl);
        verify(sendGridEmailService, times(1)).sendSimpleMessage(any());
    }

    @Test
    public void testHtmlStripping() throws Exception {
        String htmlContentNoTags = "one";
        String htmlContentWithTags = "<h1>" + htmlContentNoTags + "</h1>";

        Email email = new Email();
        email.body = htmlContentWithTags;
        try {
            emailServiceImpl.sendSimpleMessage(email);
        } catch (Exception ex) {

        }
        Assert.assertTrue(email.body.equals(htmlContentNoTags));
    }
}
