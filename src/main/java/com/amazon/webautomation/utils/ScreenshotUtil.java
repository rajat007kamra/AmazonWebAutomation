package com.amazon.webautomation.utils;

import com.amazon.webautomation.driver.DriverFactory;
import com.amazon.webautomation.reports.ExtentManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(String testName, String className) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            System.out.println(" WebDriver is null. Cannot take screenshot.");
            return null;
        }

        String timestamp = new SimpleDateFormat("HHmmss").format(new Date());

        //  Base report folder
        String reportFolder = ExtentManager.getReportFolderPath();
        String screenshotSubfolder = "screenshots/" + className;
        String screenshotFileName = testName + "_" + timestamp + ".png";

        //  Full path for saving screenshot
        String fullPath = reportFolder + "/" + screenshotSubfolder + "/" + screenshotFileName;

        //  Relative path from report HTML (used by ExtentReports)
        String relativePath = screenshotSubfolder + "/" + screenshotFileName;

        try {
            Files.createDirectories(new File(reportFolder + "/" + screenshotSubfolder).toPath());
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(fullPath);
            Files.copy(srcFile.toPath(), destFile.toPath());

            //  Return relative path to be used inside HTML report
            return relativePath.replace("\\", "/");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
