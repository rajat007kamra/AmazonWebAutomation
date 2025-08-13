package com.amazon.webautomation.Practise;


import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import com.amazon.webautomation.Practise.SpecFactory;

public class practiceGetRequest {

    public static void main(String[] args) {

        // Send GET request with logging and specs
        Response response = given()
                .spec(SpecFactory.getRequestSpec())
                .log().all()  // Log Request
                .when()
                .get("/users")
                .then()
                .log().all()  // Log Response
                .spec(SpecFactory.getResponseSpec())
                .extract().response();

        // Extracting specific value from response
        String firstEmail = response.path("data[0].email");
        System.out.println("First User's Email: " + firstEmail);
    }
}



