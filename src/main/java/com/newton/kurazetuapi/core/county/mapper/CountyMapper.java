package com.newton.kurazetuapi.core.county.mapper;

import com.newton.kurazetuapi.core.county.dto.CountyDto;
import com.newton.kurazetuapi.core.county.models.County;
import org.springframework.stereotype.Component;

@Component
public class CountyMapper {

    public CountyDto toDto(County county) {
        if (county == null) {
            return null;
        }

        return CountyDto.builder()
                .id(county.getId())
                .name(county.getName())
                .code(county.getCode())
                .description(county.getDescription())
                .createdAt(county.getCreatedAt())
                .build();
    }

    public County toEntity(CountyDto dto) {
        if (dto == null) {
            return null;
        }

        County county = new County();
        county.setId(dto.getId());
        county.setName(dto.getName());
        county.setCode(dto.getCode());
        county.setDescription(dto.getDescription());
        county.setCreatedAt(dto.getCreatedAt());

        return county;
    }
}
