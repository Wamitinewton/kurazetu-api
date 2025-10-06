package com.newton.kurazetuapi.core.email.messaging.service;

import com.newton.kurazetuapi.core.email.messaging.dto.EmailEvent;
import com.newton.kurazetuapi.shared.enums.EmailType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailManagementServiceImpl implements EmailManagementService {

    private final EmailProducerService emailProducerService;

    @Override
    public void sendVerificationEmailAsync(String email, String verificationToken) {
        log.info("Queueing verification email for email: {}", email);

        Map<String, Object> variables = Map.of(
                "verificationLink", "https://genzchange.or.ke/verify?token=" + verificationToken,
                "timestamp", LocalDateTime.now().toString()
        );

        EmailEvent emailEvent = EmailEvent.builder()
                .eventId(generateEventId())
                .emailType(EmailType.EMAIL_VERIFICATION)
                .recipient(email)
                .subject("🗳️ Verify Your Voter Commitment - GenZChange")
                .templateName("verify-email")
                .templateVariables(variables)
                .isHtml(true)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .build();

        emailProducerService.sendEmailEvent(emailEvent);
        log.debug("Verification email queued successfully: eventId={}", emailEvent.getEventId());
    }

    @Override
    public void sendCongratsEmailAsync(String email) {
        log.info("Queueing voter confirmation email for: {}", email);

        Map<String, Object> variables = Map.of(
                "message", "Congratulations! You’ve confirmed your commitment to vote and bring change.",
                "timestamp", LocalDateTime.now().toString()
        );

        EmailEvent emailEvent = EmailEvent.builder()
                .eventId(generateEventId())
                .emailType(EmailType.REGISTRATION_SUCCESS)
                .recipient(email)
                .subject("🎉 You’re Officially a Change Maker - GenZChange")
                .templateName("congrats-email")
                .templateVariables(variables)
                .isHtml(true)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .build();

        emailProducerService.sendEmailEvent(emailEvent);
        log.debug("Congrats email queued successfully: eventId={}", emailEvent.getEventId());
    }

    private String generateEventId() {
        return "email_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
