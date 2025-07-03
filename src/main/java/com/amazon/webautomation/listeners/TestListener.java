package com.amazon.webautomation.listeners;

import com.amazon.webautomation.reports.ExtentManager;
import com.amazon.webautomation.reports.ExtentTestManager;
import com.amazon.webautomation.utils.BrowserInfoUtil;
import com.amazon.webautomation.utils.LogUtil;
import com.amazon.webautomation.utils.ScreenshotUtil;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener implements ITestListener, IAnnotationTransformer {

    private static final ExtentReports extent = ExtentManager.getExtent();
    private static final ThreadLocal<Integer> testCounter = ThreadLocal.withInitial(() -> 1);

    @Override
    public void onStart(ITestContext context) {
        testCounter.set(1); // Reset counter
        System.out.println("\u001B[35m📢 Starting Test Execution...\u001B[0m");
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtil.resetSteps();

        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        String className = result.getTestClass().getRealClass().getSimpleName();

        // ✅ Create parent test if not already created
        ExtentTest parent = ExtentTestManager.getParentTest(className);
        if (parent == null) {
            parent = extent.createTest(className + " Module");
            ExtentTestManager.setParentTest(className, parent);
        }

        // ✅ Create child test node under the module
        ExtentTest childTest = parent.createNode(methodName, description != null ? description : "");

        // ✅ Add metadata
        for (String group : result.getMethod().getGroups()) {
            childTest.assignCategory(group);
        }
        childTest.assignAuthor("Rajat Kamra");
        childTest.assignDevice(BrowserInfoUtil.getDeviceName());

        // ✅ Register this test to thread
        ExtentTestManager.setTest(childTest);

        // ✅ Print test number in console
        int count = testCounter.get();
        System.out.println("\u001B[34m" +
                "===============================" +
                "\n Test Case " + count + " :: Executing " + className + " → " + methodName +
                "\n===============================" + "\u001B[0m");
        testCounter.set(count + 1);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, " Test passed");
        System.out.println("\u001B[32m PASSED: " + result.getTestClass().getName() + " → " + result.getMethod().getMethodName() + "\u001B[0m");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        ExtentTest test = ExtentTestManager.getTest();

        test.fail(" Test failed: " + result.getThrowable());

        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(testName, className);
            if (screenshotPath != null) {
                test.fail(" Screenshot of failure:",
                        com.aventstack.extentreports.MediaEntityBuilder
                                .createScreenCaptureFromPath(screenshotPath)
                                .build());
            } else {
                test.warning("️ Screenshot path was null");
            }
        } catch (Exception e) {
            test.warning("️ Could not attach screenshot: " + e.getMessage());
        }

        System.out.println("\u001B[31m FAILED: " + className + " → " + testName + "\u001B[0m");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Throwable cause = result.getThrowable();
        ExtentTestManager.getTest().log(
                Status.SKIP,
                " Test skipped" + (cause != null ? ": " + cause.getMessage() : "")
        );
        System.out.println("\u001B[33m SKIPPED: " + result.getTestClass().getName() + " → " + result.getMethod().getMethodName() + "\u001B[0m");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();  // Finalize report
        System.out.println("\u001B[35m TEST EXECUTION SUMMARY\u001B[0m");
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);  // Attach retry analyzer
    }
}
