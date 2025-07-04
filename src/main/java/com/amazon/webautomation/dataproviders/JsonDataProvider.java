package com.amazon.webautomation.dataproviders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class JsonDataProvider {

    @DataProvider(name = "jsonDataProvider")
    public static Object[][] provideData(Method method) throws Exception {
        String methodName = method.getName();
        String className = method.getDeclaringClass().getSimpleName();
        String path = System.getProperty("user.dir")
                + "/src/test/resources/testdata/" + className + "Data.json";

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);

        if (!file.exists()) {
            throw new SkipException(" Data file not found: " + path);
        }

        JsonNode root = mapper.readTree(file);
        JsonNode dataNode = root.get(methodName);

        if (dataNode == null || !dataNode.isArray() || dataNode.size() == 0) {
            throw new SkipException(" No data for method: " + methodName + " in " + path);
        }

        List<Map<String, String>> dataList = mapper.readValue(
                dataNode.toString(), new TypeReference<List<Map<String, String>>>() {}
        );

        return dataList.stream().map(d -> new Object[]{d}).toArray(Object[][]::new);
    }
}
