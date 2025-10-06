package com.newton.kurazetuapi.core.email.mapper;

import com.newton.kurazetuapi.core.email.dto.EmailConfirmationDto;
import com.newton.kurazetuapi.core.email.models.EmailConfirmation;
import org.springframework.stereotype.Component;

@Component
public class EmailConfirmationMapper {

    public EmailConfirmationDto toDto(EmailConfirmation confirmation) {
        if (confirmation == null) {
            return null;
        }

        return EmailConfirmationDto.builder()
                .id(confirmation.getId())
                .voterRegistrationId(confirmation.getVoterRegistration().getId())
                .token(confirmation.getToken())
                .expiryDate(confirmation.getExpiryDate())
                .used(confirmation.getUsed())
                .usedAt(confirmation.getUsedAt())
                .expired(confirmation.isExpired())
                .build();
    }
}
