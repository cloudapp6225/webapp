package com.cloudnativewebapp.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
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

		SpringApplication.run(WebAppApplication.class, args);
	}

}
