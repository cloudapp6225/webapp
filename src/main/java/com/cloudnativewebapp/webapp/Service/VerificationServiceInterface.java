package com.cloudnativewebapp.webapp.Service;

public interface VerificationServiceInterface {

    public void verfiyEmail(String userId);

    public String getVerificationStatus(String email);
}
