package com.cloudnativewebapp.webapp;

import com.cloudnativewebapp.webapp.SMTP.MailgunSMTP;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
//		MailgunSMTP mailgunSMTP = new MailgunSMTP();
//		mailgunSMTP.sendEmail("hi");
	}

}
