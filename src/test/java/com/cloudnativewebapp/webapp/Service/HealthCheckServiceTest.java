package com.cloudnativewebapp.webapp.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HealthCheckServiceTest {

    @Autowired
    private HealthCheckService healthCheckService;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        Mockito.when(jdbcTemplate.queryForObject("SELECT  1", Integer.class))
                .thenReturn(1);
    }

//    @Test
//    void isDBConnected() {
//        boolean healthCheck = healthCheckService.isDBConnected();
//        assertEquals(healthCheck, true);
//    }
}