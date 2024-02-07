package com.cloudnativewebapp.webapp;

import com.cloudnativewebapp.webapp.Service.DatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
		DatabaseService dbService = new DatabaseService();
		dbService.createDatabase();
		SpringApplication.run(WebAppApplication.class, args);
	}

}
