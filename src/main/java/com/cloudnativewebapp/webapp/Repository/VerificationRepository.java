package com.cloudnativewebapp.webapp.Repository;

import com.cloudnativewebapp.webapp.Entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VerificationRepository extends JpaRepository<EmailVerification, String> {
    public EmailVerification findByUserId(String userId);

    public EmailVerification findByEmail(String Email);
}
