package com.amazon.webautomation.reports;

import com.amazon.webautomation.config.ConfigReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static String reportFolderPath;

    public static ExtentReports getExtent() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportFolderPath = "test-output/ExtentReports/" + timestamp;
            String reportPath = reportFolderPath + "/ExtentReport.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("AJIO Web Automation Report");
            spark.config().setDocumentTitle("Test Execution Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System Info (passed via Jenkins or default config)
            extent.setSystemInfo("Tester", "Rajat Kamra");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Browser", ConfigReader.get("browser", "chrome"));
            extent.setSystemInfo("Suite", ConfigReader.get("suite", "smoke"));
            extent.setSystemInfo("Branch", ConfigReader.get("branch", "qa"));

            new File(reportFolderPath).mkdirs();
        }
        return extent;
    }

    public static String getReportFolderPath() {
        return reportFolderPath;
    }
}
