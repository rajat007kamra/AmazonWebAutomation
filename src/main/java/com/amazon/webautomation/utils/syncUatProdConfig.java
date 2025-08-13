package com.amazon.webautomation.utils;

import com.amazon.webautomation.config.ConfigReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class syncUatProdConfig {

    public static void syncUatConfigWithProd(
            String prodServiceAccountPath,
            String prodProjectId,
            String uatServiceAccountPath,
            String uatProjectId,
            String outputPath
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Step 1: Fetch prod and uat flags
            JsonNode prodFlags = FirebaseFlagFetcher.fetchEnvFlags(prodServiceAccountPath, prodProjectId, ConfigReader.get("prodEnv"));
            JsonNode uatFlags = FirebaseFlagFetcher.fetchEnvFlags(uatServiceAccountPath, uatProjectId, ConfigReader.get("uatEnv"));

            // Step 2: Convert to maps
            LinkedHashMap<String, Object> prodMap = mapper.convertValue(prodFlags, new TypeReference<>() {});
            LinkedHashMap<String, Object> uatMap = mapper.convertValue(uatFlags, new TypeReference<>() {});

            // Step 3: Merge while preserving prod order and using uat values for common keys
            LinkedHashMap<String, Object> mergedMap = new LinkedHashMap<>();

            for (Map.Entry<String, Object> prodEntry : prodMap.entrySet()) {
                String key = prodEntry.getKey();
                Object finalValue;
                if (uatMap.containsKey(key)) {
                    finalValue = uatMap.get(key);
                } else {
                    finalValue = prodEntry.getValue();
                }
                mergedMap.put(key, finalValue);
            }

            // Step 4: Add extra keys from uat (not in prod)
            for (Map.Entry<String, Object> uatEntry : uatMap.entrySet()) {
                if (!mergedMap.containsKey(uatEntry.getKey())) {
                    mergedMap.put(uatEntry.getKey(), uatEntry.getValue());
                }
            }

            // Step 5: Wrap mergedMap with "env" key
            LinkedHashMap<String, Object> rootWrapper = new LinkedHashMap<>();
            rootWrapper.put(ConfigReader.get("uatEnv"), mergedMap);

            // Step 6: Write to output
            System.out.println("Total no of flags after Sync :: " + mergedMap.size());
            mapper.writeValue(new File(outputPath), rootWrapper);
            System.out.println("Merged config written to: " + outputPath);

        } catch (Exception e) {
            System.err.println(" Error merging Firebase configs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        syncUatConfigWithProd(
                "src/test/resources/firebase/" + ConfigReader.get("prodServiceAccount") + ".json",
                ConfigReader.get("prodProjectId"),
                "src/test/resources/firebase/" + ConfigReader.get("uatServiceAccount") + ".json",
                ConfigReader.get("uatProjectId"),
                "output.json"
        );
    }
}
