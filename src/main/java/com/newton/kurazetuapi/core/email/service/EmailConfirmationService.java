package com.newton.kurazetuapi.core.email.service;

import com.newton.kurazetuapi.core.email.models.EmailConfirmation;
import com.newton.kurazetuapi.core.voter.models.VoterRegistration;

public interface EmailConfirmationService {

    EmailConfirmation createEmailConfirmation(VoterRegistration voterRegistration);
    EmailConfirmation verifyToken(String token);
    void markAsUsed(EmailConfirmation emailConfirmation);
}
