package kkolodziejski.emailService.controller;

import kkolodziejski.emailService.model.EmailMessage;
import kkolodziejski.emailService.service.CustomerService;
import kkolodziejski.emailService.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emails")
public class EmailController {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/toSubscribers")
    ResponseEntity<String> sendEmails(@RequestBody EmailMessage message) {

        customerService.findAll().forEach(customer ->
                emailSenderService.sendEmail(sender, customer.getEmail(), message.getSubject(), message.getBody()));

        return new ResponseEntity<>("Messages have been sent!", HttpStatus.OK);
    }

}
