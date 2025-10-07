package com.newton.kurazetuapi.core.stats.service;

import com.newton.kurazetuapi.core.county.models.County;
import com.newton.kurazetuapi.core.county.repositories.CountyRepository;
import com.newton.kurazetuapi.core.stats.dto.AllCountiesStatsDto;
import com.newton.kurazetuapi.core.stats.dto.CountyStatsDto;
import com.newton.kurazetuapi.core.stats.dto.OverallStatsDto;
import com.newton.kurazetuapi.core.voter.repository.VoterRegistrationRepository;
import com.newton.kurazetuapi.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final VoterRegistrationRepository voterRegistrationRepository;
    private final CountyRepository countyRepository;

    @Override
    public OverallStatsDto getOverallStats() {
        Long totalVerified = voterRegistrationRepository.countConfirmedRegistrations();

        return OverallStatsDto.builder()
                .totalVerifiedRegistrations(totalVerified)
                .build();
    }

    @Override
    public CountyStatsDto getCountyStats(Long countyId) {
        County county = countyRepository.findById(countyId)
                .orElseThrow(() -> new CustomException("County not found with id: " + countyId));

        Long countyTotal = voterRegistrationRepository.countByCountyId(countyId);

        return CountyStatsDto.builder()
                .countyId(county.getId())
                .countyName(county.getName())
                .countyCode(county.getCode())
                .totalVerifiedRegistrations(countyTotal)
                .build();
    }

    @Override
    public AllCountiesStatsDto getAllCountiesStats() {
        Long totalVerified = voterRegistrationRepository.countConfirmedRegistrations();

        List<County> counties = countyRepository.findAll();

        List<CountyStatsDto> countyStats = counties.stream()
                .map(county -> {
                    Long countyTotal = voterRegistrationRepository.countByCountyId(county.getId());
                    return CountyStatsDto.builder()
                            .countyId(county.getId())
                            .countyName(county.getName())
                            .countyCode(county.getCode())
                            .totalVerifiedRegistrations(countyTotal)
                            .build();
                })
                .collect(Collectors.toList());

        return AllCountiesStatsDto.builder()
                .totalVerifiedRegistrations(totalVerified)
                .countyStats(countyStats)
                .build();
    }
}