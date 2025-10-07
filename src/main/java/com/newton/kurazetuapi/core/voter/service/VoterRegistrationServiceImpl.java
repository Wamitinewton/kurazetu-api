package com.newton.kurazetuapi.core.voter.service;

import com.newton.kurazetuapi.core.county.models.County;
import com.newton.kurazetuapi.core.county.repositories.CountyRepository;
import com.newton.kurazetuapi.core.email.messaging.service.EmailManagementService;
import com.newton.kurazetuapi.core.email.models.EmailConfirmation;
import com.newton.kurazetuapi.core.email.service.EmailConfirmationService;
import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationRequestDto;
import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationResponseDto;
import com.newton.kurazetuapi.core.voter.mapper.VoterRegistrationMapper;
import com.newton.kurazetuapi.core.voter.models.VoterRegistration;
import com.newton.kurazetuapi.core.voter.repository.VoterRegistrationRepository;
import com.newton.kurazetuapi.shared.exceptions.AlreadyExistsException;
import com.newton.kurazetuapi.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class VoterRegistrationServiceImpl implements VoterRegistrationService {

    private final VoterRegistrationRepository voterRegistrationRepository;
    private final CountyRepository countyRepository;
    private final EmailConfirmationService emailConfirmationService;
    private final EmailManagementService emailManagementService;
    private final VoterRegistrationMapper voterRegistrationMapper;


    @Override
    public VoterRegistrationResponseDto registerVoter(VoterRegistrationRequestDto voterRegistrationRequestDto, String ipAddress) {
        if (voterRegistrationRepository.existsByEmail(voterRegistrationRequestDto.getEmail())) {
            throw new AlreadyExistsException("Email has already been registered");
        }

        if (voterRegistrationRepository.existsByIpAddress(ipAddress)) {
            throw new AlreadyExistsException("A registration from this device already exists");
        }

        County county = countyRepository.findById(voterRegistrationRequestDto.getCountyId())
                .orElseThrow(() -> new CustomException("County not found"));

        VoterRegistration registration = voterRegistrationMapper.toEntity(voterRegistrationRequestDto, county, ipAddress);
        VoterRegistration savedRegistration = voterRegistrationRepository.save(registration);
        EmailConfirmation confirmation = emailConfirmationService.createEmailConfirmation(savedRegistration);
        emailManagementService.sendVerificationEmailAsync(savedRegistration.getEmail(), confirmation.getToken());

        return voterRegistrationMapper.toResponseDto(savedRegistration);
    }

    @Override
    public VoterRegistrationResponseDto confirmVoterRegistration(String token) {
        EmailConfirmation confirmation = emailConfirmationService.verifyToken(token);
        VoterRegistration registration = confirmation.getVoterRegistration();

        if (registration.getConfirmed()) {
            throw new CustomException("Voter registration has already been confirmed");
        }

        registration.setConfirmed(true);
        registration.setConfirmedAt(LocalDateTime.now());
        VoterRegistration updatedRegistration = voterRegistrationRepository.save(registration);

        emailConfirmationService.markAsUsed(confirmation);
        emailManagementService.sendCongratsEmailAsync(registration.getEmail());

        return voterRegistrationMapper.toResponseDto(updatedRegistration);
    }
}
