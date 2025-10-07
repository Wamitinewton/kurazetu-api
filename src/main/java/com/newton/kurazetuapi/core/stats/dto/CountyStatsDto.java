package com.newton.kurazetuapi.core.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountyStatsDto {
    private Long countyId;
    private String countyName;
    private String countyCode;
    private Long totalVerifiedRegistrations;
}