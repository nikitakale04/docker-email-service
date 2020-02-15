package com.brightwheel.microservices.emailservice.services;

import com.brightwheel.microservices.emailservice.pojos.Email;

public interface EmailService {
    public void sendSimpleMessage(Email email) throws Exception;
}
