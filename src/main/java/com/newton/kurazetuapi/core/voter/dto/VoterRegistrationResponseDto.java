package com.newton.kurazetuapi.core.voter.dto;

import com.newton.kurazetuapi.core.county.dto.CountyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoterRegistrationResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private CountyDto county;
    private LocalDateTime registrationDate;
    private Boolean confirmed;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
}

