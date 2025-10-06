package com.newton.kurazetuapi.core.email.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newton.kurazetuapi.shared.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailEvent {

    private String eventId;
    private EmailType emailType;
    private String recipient;
    private String subject;
    private String templateName;
    private Map<String, Object> templateVariables;
    private boolean isHtml;
    private LocalDateTime createdAt;
    private String voterId;
    private int retryCount;
    private LocalDateTime scheduledAt;


    public static EmailEvent emailVerification(String recipient, String voterName, String verificationLink, String voterId) {
        Map<String, Object> variables = Map.of(
                "name", voterName,
                "verificationLink", verificationLink
        );

        return EmailEvent.builder()
                .eventId(generateEventId())
                .emailType(EmailType.EMAIL_VERIFICATION)
                .recipient(recipient)
                .subject("🗳️ Verify Your Email - GenZKura")
                .templateName("email-verification")
                .templateVariables(variables)
                .isHtml(true)
                .voterId(voterId)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static EmailEvent registrationSuccess(String recipient, String voterName, String voterId) {
        Map<String, Object> variables = Map.of(
                "name", voterName,
                "timestamp", LocalDateTime.now().toString()
        );

        return EmailEvent.builder()
                .eventId(generateEventId())
                .emailType(EmailType.REGISTRATION_SUCCESS)
                .recipient(recipient)
                .subject("✅ Registration Confirmed - GenZKura")
                .templateName("registration-success")
                .templateVariables(variables)
                .isHtml(true)
                .voterId(voterId)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private static String generateEventId() {
        return "email_" + System.currentTimeMillis() + "_" +
                java.util.UUID.randomUUID().toString().substring(0, 8);
    }

    public void incrementRetryCount() {
        this.retryCount++;
    }
}

