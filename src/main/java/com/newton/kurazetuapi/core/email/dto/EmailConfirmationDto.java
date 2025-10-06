package com.newton.kurazetuapi.core.email.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfirmationDto {
    private Long id;
    private Long voterRegistrationId;
    private String token;
    private LocalDateTime expiryDate;
    private Boolean used;
    private LocalDateTime usedAt;
    private Boolean expired;
}
