package com.amazon.webautomation.Practise;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class practicePostRequest {

    public static void main(String[] args) throws IOException {

        JsonPathNestedValidation();

        // Base URI
        RestAssured.baseURI = "https://reqres.in/api";

        // JSON Body
        String requestBody = """
                {
                  "name": "Rajat",
                  "job": "SDET"
                }
                """;

        // Send POST Request
        Response response = given()
                .header("Content-Type", "application/json")   // Header
                .body(requestBody)                            // JSON body
                .when()
                .post("/users")                               // Endpoint
                .then()
                .assertThat()
                .statusCode(201)                              // Created
                .body("name", equalTo("Rajat"))               // Validate response
                .body("job", equalTo("SDET"))
                .extract()
                .response();

        // Print response
        System.out.println("Response:\n" + response.asPrettyString());

    }

    public static void JsonPathNestedValidation() throws IOException {

        // Load JSON from file (you can replace this with inline JSON string)
        String json = new String(Files.readAllBytes(Paths.get("src/test/java/com/amazon/webautomation/Practise/employeeDetails.json")));
        JsonPath jp = new JsonPath(json);

        System.out.println("1️⃣ Company Name: " + jp.getString("company"));

        System.out.println("2️⃣ First employee's name: " + jp.getString("employees[0].name"));

        System.out.println("3️⃣ All employee names: " + jp.getList("employees.name"));

        System.out.println("4️⃣ All employee cities: " + jp.getList("employees.address.city"));

        System.out.println("5️⃣ Zip code of Anjali: " + jp.getString("employees.find { it.name == 'Anjali' }.address.zip"));

        System.out.println("6️⃣ Active employee names: " + jp.getList("employees.findAll { it.isActive == true }.name"));

        System.out.println("7️⃣ Second skill of first employee: " + jp.getString("employees[0].skills[1]"));

        System.out.println("8️⃣ Skills of active employees: " + jp.getList("employees.findAll { it.isActive }.skills.flatten()"));

        System.out.println("9️⃣ Project names of Rajat: " + jp.getList("employees.find { it.name == 'Rajat' }.projects.name"));

        System.out.println("🔟 All project names with status 'Ongoing': " +
                jp.getList("employees.projects.flatten().findAll { it.status == 'Ongoing' }.name"));

        System.out.println("1️⃣1️⃣ Employees with no projects: " +
                jp.getList("employees.findAll { it.projects.isEmpty() }.name"));

        System.out.println("1️⃣2️⃣ Employees with null address: " +
                jp.getList("employees.findAll { it.address == null }.name"));

        System.out.println("1️⃣3️⃣ Number of active employees: " +
                jp.getList("employees.findAll { it.isActive }").size());

        System.out.println("1️⃣4️⃣ Map of employee names to cities (null-safe):");
        List<Map<String, Object>> employees = jp.getList("employees");
        for (Map<String, Object> emp : employees) {
            String name = (String) emp.get("name");
            Map<String, String> address = (Map<String, String>) emp.get("address");
            String city = (address != null) ? address.get("city") : "N/A";
            System.out.println("   - " + name + " lives in " + city);
        }

        System.out.println("1️⃣5️⃣ Does any employee have 'API' skill? " +
                jp.getList("employees.skills.flatten()").contains("API"));

        System.out.println("1️⃣6️⃣ Project names for employee with ID 103: " +
                jp.getList("employees.find { it.id == 103 }.projects.name"));

        System.out.println("1️⃣7️⃣ All zip codes (ignoring nulls): " +
                jp.getList("employees.findAll { it.address != null }.address.zip"));
    }

}

