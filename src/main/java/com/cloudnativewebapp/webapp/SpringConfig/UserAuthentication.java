package com.cloudnativewebapp.webapp.SpringConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.cloudnativewebapp.webapp.SpringConfig.AppConfig.passwordEncoder;

@Component
public class UserAuthentication implements AuthenticationProvider {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, BadCredentialsException {
        String userName = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        if(userDetails != null) {
            if(passwordEncoder().matches(password, userDetails.getPassword())) {
                return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
            }
        }

        throw new BadCredentialsException("Bad User Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
