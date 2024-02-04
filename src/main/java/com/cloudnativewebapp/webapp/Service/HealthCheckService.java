package com.cloudnativewebapp.webapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean isDBConnected() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return true;
        }
        catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
