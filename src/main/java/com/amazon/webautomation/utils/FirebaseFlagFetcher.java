package com.amazon.webautomation.utils;

import com.amazon.webautomation.config.ConfigReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.util.Collections;

public class FirebaseFlagFetcher {

    private static final String FIREBASE_REMOTE_CONFIG_ENDPOINT =
            "https://firebaseremoteconfig.googleapis.com/v1/projects/%s/remoteConfig";

    public static JsonNode fetchEnvFlags(String serviceAccountPath, String projectId, String env) throws Exception {
        // Load and authorize credentials
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(serviceAccountPath))
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.remoteconfig"));

        credentials.refreshIfExpired();

        String accessToken = credentials.getAccessToken().getTokenValue();

        // Build request URL
        String url = String.format(FIREBASE_REMOTE_CONFIG_ENDPOINT, projectId);

        printCurlForRequest(url,accessToken);

        // Use Rest Assured to make GET call
        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept-Encoding", "gzip")
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Parse the JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody().asString());

        JsonNode paramNode = root.path("parameters").path("web_feature_flags");
        JsonNode valueNode = paramNode.path("defaultValue").path("value");

        // If the value is a raw string, parse it
        if (valueNode.isTextual()) {
            valueNode = mapper.readTree(valueNode.asText());
        }

        // Now extract the environment-specific node (like "production", "uat62")
        JsonNode envFlags = valueNode.path(env);

        // Fallback: if that environment doesn't exist, warn and return full value node
        if (envFlags.isMissingNode()) {
            System.err.println(" Environment key '" + env + "' not found in Firebase config. Returning full value.");
            return valueNode;
        }
        return envFlags;
    }

    public static void printCurlForRequest(String url, String accessToken) {
        String curlCommand = String.format(
                "curl -X GET \"%s\" \\\n" +
                        "  -H \"Authorization: Bearer %s\" \\\n" +
                        "  -H \"Accept-Encoding: gzip\"",
                url, accessToken
        );
        System.out.println("Equivalent cURL command:");
        System.out.println(curlCommand);
    }

    // Driver Code
    public static void main(String[] args) {
        try {
            String serviceAccountPath = "src/test/resources/firebase/ajio-uat-service-account.json";
            String projectId = "ajio-uat";
            String env = "uat62"; // For Production - production
            JsonNode envFlags = fetchEnvFlags(serviceAccountPath, projectId, env);
            System.out.println(" Flags for environment [" + env + "]:");
            System.out.println(envFlags.toPrettyString());
            System.out.println("No of flags in "+ env + " :: "+envFlags.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
