package com.cloudnativewebapp.webapp.IntegrationTests;

import com.cloudnativewebapp.webapp.Entity.EmailVerification;
import com.cloudnativewebapp.webapp.Repository.VerificationRepository;
import com.cloudnativewebapp.webapp.Service.VerificationServiceInterface;
import com.cloudnativewebapp.webapp.WebAppApplication;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebappTests {

    @Autowired
    VerificationRepository verificationRepository;

    @BeforeAll
    static void setUp() {
        SpringApplication.run(WebAppApplication.class);
    }

    @Test
    @Order(1)
    public void createUserTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"first_name\": \"Jane\", \"last_name\": \"Doe\", \"password\": \"skdjfhskdfjhg\", \"username\": \"vinay21031998@gmail.com\"}")
                .when()
                .post("/v2/user")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        EmailVerification emailVerification = verificationRepository.findByEmail("vinay21031998@gmail.com");
        emailVerification.setStatus("success");
        verificationRepository.save(emailVerification);

        given()
                .auth().basic("vinay21031998@gmail.com", "skdjfhskdfjhg")
                .when()
                .get("/v2/user/self")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("first_name", equalTo("Jane"))
                .body("last_name", equalTo("Doe"))
                .body("password", nullValue())
                .body("username", equalTo("vinay21031998@gmail.com"));
    }

    @Test
    @Order(2)
    public void updateUserTest() {
        given()
                .contentType(ContentType.JSON)
                .auth().basic("vinay21031998@gmail.com", "skdjfhskdfjhg")
                .body("{\"first_name\": \"vk\", \"last_name\": \"l\", \"password\": \"XXX\"}")
                .when()
                .put("/v2/user/self")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given()
                .auth().basic("vinay21031998@gmail.com", "XXX")
                .when()
                .get("/v2/user/self")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("first_name", equalTo("vk"))
                .body("last_name", equalTo("l"))
                .body("password", nullValue())
                .body("username", equalTo("vinay21031998@gmail.com"));
    }
}
