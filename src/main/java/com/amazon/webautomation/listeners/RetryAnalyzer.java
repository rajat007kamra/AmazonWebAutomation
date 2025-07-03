package com.amazon.webautomation.listeners;

import com.amazon.webautomation.config.ConfigReader;
import com.amazon.webautomation.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount;

    public RetryAnalyzer() {
        int configuredRetryCount = 0;
        try {
            configuredRetryCount = Integer.parseInt(ConfigReader.get("retryCount"));
        } catch (NumberFormatException e) {
            System.err.println("️ Invalid retryCount in config.properties. Defaulting to 0 retries.");
        }
        this.maxRetryCount = configuredRetryCount;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;

            String msg = " Retry attempt " + retryCount + " of " + maxRetryCount
                    + " for test: " + result.getMethod().getMethodName();

            System.out.println(msg);

            // Log to Extent Report (if available)
            try {
                ExtentTestManager.getTest().log(Status.WARNING, msg);
            } catch (Exception e) {
                // May fail if ExtentTest wasn't initialized yet
                System.err.println(" Could not log retry to ExtentTest: " + e.getMessage());
            }

            return true;
        }
        return false;
    }
}
