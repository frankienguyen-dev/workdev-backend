package com.frankie.workdev.controller;

import com.frankie.workdev.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Send email controller",
        description = "Controller for sending email"
)
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/send-email")
public class SendEmailController {
    private EmailService emailService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Schedule email",
            description = "Schedule email controller is used to schedule email in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Email scheduled successfully"
    )
    @PostMapping("/schedule-email")
    public ResponseEntity<String> scheduleEmail()
            throws MessagingException {
        emailService.scheduleEmail();
        return new ResponseEntity<>("Email scheduled successfully", HttpStatus.OK);
    }
}
