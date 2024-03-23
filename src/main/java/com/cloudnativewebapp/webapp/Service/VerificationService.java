package com.cloudnativewebapp.webapp.Service;


import com.cloudnativewebapp.webapp.Entity.EmailVerification;
import com.cloudnativewebapp.webapp.Repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VerificationService implements VerificationServiceInterface {

    @Autowired
    VerificationRepository verificationRepository;
    @Override
    public void verfiyEmail(String userId) {
        EmailVerification emailVerification = verificationRepository.findByUserId(userId);
        emailVerification.setVerified_timestamp(String.valueOf(LocalDateTime.now()));
        emailVerification.setStatus("success");
        verificationRepository.save(emailVerification);
    }

    @Override
    public String getVerificationStatus(String email) {
        EmailVerification emailVerification = verificationRepository.findByEmail(email);
        return emailVerification.getStatus();
    }


    @Override
    public EmailVerification saveEmaillink(String email, String userId) {
        EmailVerification emailVerification = EmailVerification.builder()
                .email(email)
                .userId(userId)
                .sent_timestamp(String.valueOf(LocalDateTime.now()))
                .verified_timestamp(null)
                .status("sent").build();

        verificationRepository.save(emailVerification);
        return emailVerification;
    }
}
