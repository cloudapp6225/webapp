package com.cloudnativewebapp.webapp.Service;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService {
    public void createDatabase() {
        // BootStrap database on startup
        String dataSourceURL = "jdbc:postgresql://localhost:5432/postgres";
        String username = "web_app";
        String password = "ZLce#E3O0oS!w51@+h@d";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dataSourceURL);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            jdbcTemplate.execute("CREATE DATABASE app_db;");
            System.out.println("db_test Created");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
