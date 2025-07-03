package com.amazon.webautomation.driver;

import com.amazon.webautomation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver initializeDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean isHeadless = ConfigReader.getBoolean("headless");

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) chromeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                tlDriver.set(new ChromeDriver(chromeOptions));
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) edgeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                tlDriver.set(new EdgeDriver(edgeOptions));
                break;

            default:
                throw new RuntimeException(" Unsupported browser: " + browser);
        }

        return getDriver();
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
