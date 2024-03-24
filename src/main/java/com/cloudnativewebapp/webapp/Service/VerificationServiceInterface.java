package com.cloudnativewebapp.webapp.Service;

import com.cloudnativewebapp.webapp.Entity.EmailVerification;

public interface VerificationServiceInterface {

    public String verfiyEmail(String userId);

    public String getVerificationStatus(String email);

    public EmailVerification saveEmaillink(String email, String userId);
}
