package com.newton.kurazetuapi.core.email.messaging.service;

public interface EmailManagementService {

    void sendVerificationEmailAsync(String email, String verificationToken);

    void sendCongratsEmailAsync(String email);
}
