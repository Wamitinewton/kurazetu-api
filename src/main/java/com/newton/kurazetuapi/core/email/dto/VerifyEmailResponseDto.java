package com.newton.kurazetuapi.core.email.dto;

import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailResponseDto {
    private VoterRegistrationResponseDto voterRegistration;
}

