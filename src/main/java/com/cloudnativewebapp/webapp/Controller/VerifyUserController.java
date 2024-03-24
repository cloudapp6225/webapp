package com.cloudnativewebapp.webapp.Controller;


import com.cloudnativewebapp.webapp.Service.VerificationServiceInterface;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyUserController {

    @Autowired
    private VerificationServiceInterface verificationService;

    @GetMapping("/v1/verify/{id}")
    public ResponseEntity<String> verifyUser(@PathVariable String id) {
        String status = verificationService.verfiyEmail(id);
        return ResponseEntity.status(HttpStatus.SC_OK).body(status);
    }
}
