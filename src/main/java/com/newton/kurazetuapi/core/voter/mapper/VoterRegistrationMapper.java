package com.newton.kurazetuapi.core.voter.mapper;

import com.newton.kurazetuapi.core.county.mapper.CountyMapper;
import com.newton.kurazetuapi.core.county.models.County;
import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationRequestDto;
import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationResponseDto;
import com.newton.kurazetuapi.core.voter.models.VoterRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoterRegistrationMapper {

    private final CountyMapper countyMapper;

    public VoterRegistrationResponseDto toResponseDto(VoterRegistration registration) {
        if (registration == null) {
            return null;
        }

        return VoterRegistrationResponseDto.builder()
                .id(registration.getId())
                .name(registration.getName())
                .age(registration.getAge())
                .email(registration.getEmail())
                .county(countyMapper.toDto(registration.getCounty()))
                .registrationDate(registration.getRegistrationDate())
                .confirmed(registration.getConfirmed())
                .confirmedAt(registration.getConfirmedAt())
                .createdAt(registration.getCreatedAt())
                .build();
    }

    public VoterRegistration toEntity(VoterRegistrationRequestDto dto, County county) {
        if (dto == null) {
            return null;
        }

        VoterRegistration registration = new VoterRegistration();
        registration.setName(dto.getName());
        registration.setAge(dto.getAge());
        registration.setEmail(dto.getEmail());
        registration.setCounty(county);
        registration.setConfirmed(false);

        return registration;
    }
}
