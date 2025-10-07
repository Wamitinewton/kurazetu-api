package com.newton.kurazetuapi.core.voter.service;

import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationRequestDto;
import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationResponseDto;

public interface VoterRegistrationService {

    VoterRegistrationResponseDto registerVoter(VoterRegistrationRequestDto voterRegistrationRequestDto, String ipAddress);
    VoterRegistrationResponseDto confirmVoterRegistration(String token);

}
