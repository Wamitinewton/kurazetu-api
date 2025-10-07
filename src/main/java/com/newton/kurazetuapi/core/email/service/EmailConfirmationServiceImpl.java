package com.newton.kurazetuapi.core.email.service;

import com.newton.kurazetuapi.core.email.models.EmailConfirmation;
import com.newton.kurazetuapi.core.email.repository.EmailConfirmationRepository;
import com.newton.kurazetuapi.core.voter.models.VoterRegistration;
import com.newton.kurazetuapi.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private final EmailConfirmationRepository emailConfirmationRepository;
    private static final int TOKEN_EXPIRY_HOURS = 24;

    @Override
    public EmailConfirmation createEmailConfirmation(VoterRegistration voterRegistration) {
        EmailConfirmation confirmation = new EmailConfirmation();
        confirmation.setVoterRegistration(voterRegistration);
        confirmation.setToken(generateToken());
        confirmation.setExpiryDate(LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS));
        confirmation.setUsed(false);

        return emailConfirmationRepository.save(confirmation);
    }

    @Override
    @Transactional(readOnly = true)
    public EmailConfirmation verifyToken(String token) {
        EmailConfirmation confirmation = emailConfirmationRepository.findByToken(token)
                .orElseThrow(() -> new CustomException("Invalid verification token"));

        if (confirmation.getUsed()) {
            throw new CustomException("This verification link has already been used");
        }

        if (confirmation.isExpired()) {
            throw new CustomException("Verification token has expired");
        }

        return confirmation;
    }

    @Override
    public void markAsUsed(EmailConfirmation emailConfirmation) {
        emailConfirmation.setUsed(true);
        emailConfirmation.setUsedAt(LocalDateTime.now());
        emailConfirmationRepository.save(emailConfirmation);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
