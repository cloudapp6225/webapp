package com.cloudnativewebapp.webapp.Service;


import com.cloudnativewebapp.webapp.Entity.EmailVerification;
import com.cloudnativewebapp.webapp.Repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class VerificationService implements VerificationServiceInterface {

    @Autowired
    VerificationRepository verificationRepository;
    @Override
    public String verfiyEmail(String userId) {
        EmailVerification emailVerification = verificationRepository.findByUserId(userId);
        String sentTimeStamp = emailVerification.getSent_timestamp();
        LocalDateTime storedTimestamp = LocalDateTime.parse(sentTimeStamp, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
        LocalDateTime currentTimestamp = LocalDateTime.now();
        long minutesDifference = Duration.between(storedTimestamp, currentTimestamp).toMinutes();
        if(minutesDifference <= 2) {
            emailVerification.setVerified_timestamp(String.valueOf(LocalDateTime.now()));
            emailVerification.setStatus("success");
            verificationRepository.save(emailVerification);
            return "success";
        }
        else {
            emailVerification.setVerified_timestamp(String.valueOf(LocalDateTime.now()));
            emailVerification.setStatus("Expired");
            verificationRepository.save(emailVerification);
            return "Link Expired";
        }
    }

    @Override
    public String getVerificationStatus(String email) {
        EmailVerification emailVerification = verificationRepository.findByEmail(email);
        return emailVerification.getStatus();
    }


    @Override
    public EmailVerification saveEmaillink(String email, String userId) {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        String formattedTimestamp = currentTimestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
        EmailVerification emailVerification = EmailVerification.builder()
                .email(email)
                .userId(userId)
                .sent_timestamp(formattedTimestamp)
                .verified_timestamp(null)
                .status("Triggered").build();

        verificationRepository.save(emailVerification);
        return emailVerification;
    }
}
