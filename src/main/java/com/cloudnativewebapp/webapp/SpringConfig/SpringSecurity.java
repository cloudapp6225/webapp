package com.cloudnativewebapp.webapp.SpringConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringSecurity {
    private static final String[] WHITELISTED_URLS = {
            "/healthz",
            "/v1/user",
            "/v1/verify/*"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth.requestMatchers(WHITELISTED_URLS).permitAll()
                                                     .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
