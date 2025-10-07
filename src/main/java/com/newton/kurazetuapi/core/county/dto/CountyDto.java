package com.newton.kurazetuapi.core.county.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountyDto {
    private Long id;
    private String name;
    private String code;
    private LocalDateTime createdAt;
}
