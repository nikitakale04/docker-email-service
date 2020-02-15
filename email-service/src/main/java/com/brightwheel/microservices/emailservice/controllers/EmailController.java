package com.brightwheel.microservices.emailservice.controllers;

import com.brightwheel.microservices.emailservice.pojos.Email;
import com.brightwheel.microservices.emailservice.services.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    public EmailController() {}

    @PostMapping("/email")
    public void postEmail(@RequestBody Email email) throws Exception {
        emailService.sendSimpleMessage(email);
    }
}
