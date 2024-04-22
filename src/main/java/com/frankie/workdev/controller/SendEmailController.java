package com.frankie.workdev.controller;

import com.frankie.workdev.dto.subscriber.CreateSubscriberDto;
import com.frankie.workdev.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/send-email")
public class SendEmailController {
    private EmailService emailService;

    @PostMapping("/schedule-email")
    public ResponseEntity<String> scheduleEmail()
            throws MessagingException {
        emailService.scheduleEmail();
        return new ResponseEntity<>("Email scheduled successfully", HttpStatus.OK);
    }
}
