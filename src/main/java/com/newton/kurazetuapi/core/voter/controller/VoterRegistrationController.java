package com.newton.kurazetuapi.core.voter.controller;

import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationRequestDto;
import com.newton.kurazetuapi.core.voter.dto.VoterRegistrationResponseDto;
import com.newton.kurazetuapi.core.voter.service.VoterRegistrationService;
import com.newton.kurazetuapi.shared.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/voter-registrations")
@RequiredArgsConstructor
public class VoterRegistrationController {

    private final VoterRegistrationService voterRegistrationService;

    @PostMapping
    public ResponseEntity<ApiResponse> registerVoter(
            @Valid @RequestBody VoterRegistrationRequestDto requestDto,
            HttpServletRequest request) {

        String ipAddress = extractIpAddress(request);
        VoterRegistrationResponseDto response = voterRegistrationService.registerVoter(requestDto, ipAddress);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Registration successful. Please check your email to verify", response));
    }

    @GetMapping("/confirm")
    public ResponseEntity<ApiResponse> confirmRegistration(@RequestParam String token) {
        VoterRegistrationResponseDto response = voterRegistrationService.confirmVoterRegistration(token);
        return ResponseEntity.ok(new ApiResponse("Voter registration confirmed successfully", response));
    }


    private String extractIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return ipAddress;
    }
}