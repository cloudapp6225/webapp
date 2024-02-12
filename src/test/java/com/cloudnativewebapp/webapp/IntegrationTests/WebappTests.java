package com.cloudnativewebapp.webapp.IntegrationTests;

import com.cloudnativewebapp.webapp.WebAppApplication;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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

    @BeforeAll
    static void setUp() {
        SpringApplication.run(WebAppApplication.class);
    }

    @Test
    @Order(1)
    public void createUserTest() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"first_name\": \"Jane\", \"last_name\": \"Doe\", \"password\": \"skdjfhskdfjhg\", \"username\": \"jane.doe@example.com\"}")
                .when()
                .post("/v1/user")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        given()
                .auth().basic("jane.doe@example.com", "skdjfhskdfjhg")
                .when()
                .get("/v1/user/self")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("first_name", equalTo("Jane"))
                .body("last_name", equalTo("Doe"))
                .body("password", nullValue())
                .body("username", equalTo("jane.doe@example.com"));
    }

    @Test
    @Order(2)
    public void updateUserTest() {
        given()
                .contentType(ContentType.JSON)
                .auth().basic("jane.doe@example.com", "skdjfhskdfjhg")
                .body("{\"first_name\": \"Kim\", \"last_name\": \"Kardashian\", \"password\": \"XXX\"}")
                .when()
                .put("/v1/user/self")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given()
                .auth().basic("jane.doe@example.com", "XXX")
                .when()
                .get("/v1/user/self")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("first_name", equalTo("Kim"))
                .body("last_name", equalTo("Kardashian"))
                .body("password", nullValue())
                .body("username", equalTo("jane.doe@example.com"));
    }
}
