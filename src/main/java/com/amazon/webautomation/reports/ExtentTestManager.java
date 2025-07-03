package com.amazon.webautomation.reports;

import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {

    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static final Map<String, ExtentTest> parentTests = new HashMap<>();

    public static void setTest(ExtentTest test) {
        testThread.set(test);
    }

    public static ExtentTest getTest() {
        return testThread.get();
    }

    public static void setParentTest(String className, ExtentTest parentTest) {
        parentTests.put(className, parentTest);
    }

    public static ExtentTest getParentTest(String className) {
        return parentTests.get(className);
    }
}
