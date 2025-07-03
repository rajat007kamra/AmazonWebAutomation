package com.amazon.webautomation.base;

import com.amazon.webautomation.config.ConfigReader;
import com.amazon.webautomation.driver.DriverFactory;
import com.amazon.webautomation.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    private ThreadLocal<Long> testStartTime = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        testStartTime.set(System.currentTimeMillis());

        driver = DriverFactory.initializeDriver();
        System.out.println("Browser launched in " + (ConfigReader.getBoolean("headless") ? "headless" : "headed") + " mode.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        long endTime = System.currentTimeMillis();
        long durationInSec = (endTime - testStartTime.get()) / 1000;

        // Log duration in Extent report
        ExtentTestManager.getTest().log(Status.INFO,
                " Total test execution time: **" + durationInSec + " seconds**");

        // Optional: Log to console
        System.out.println(" Total time for test '" + result.getMethod().getMethodName() + "': " + durationInSec + "s");

        DriverFactory.quitDriver();
        System.out.println("Browser closed.");
    }
}
