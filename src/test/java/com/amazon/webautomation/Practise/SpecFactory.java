package com.amazon.webautomation.Practise;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

public class SpecFactory {

    // Reusable Request Spec
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")
                .addHeader("Authorization", "Bearer dummy_token")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addQueryParam("page", 2)  // Default page
                .build();
    }

    // Reusable Response Spec
    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectHeader("Content-Type", containsString("application/json"))
                .expectResponseTime(lessThan(2000L))
                .expectBody("data.size()", greaterThan(0))
                .expectBody("data[0].id", notNullValue())
                .expectBody("data[0].email", containsString("@"))
                .expectBody("support.url", not(isEmptyOrNullString()))
                .build();
    }
}

