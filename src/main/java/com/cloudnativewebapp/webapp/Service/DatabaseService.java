package com.cloudnativewebapp.webapp.Service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService {
    public void createDatabase() {
        // BootStrap database on startup
        Dotenv dotenv = Dotenv.configure().load();
        String dataSourceURL = "jdbc:postgresql://localhost:5432/postgres";
        String username = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

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
