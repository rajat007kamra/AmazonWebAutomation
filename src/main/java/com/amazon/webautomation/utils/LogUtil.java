package com.amazon.webautomation.utils;

import com.amazon.webautomation.reports.ExtentTestManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class LogUtil {

    private static final ThreadLocal<Integer> stepCounter = ThreadLocal.withInitial(() -> 1);
    private static final ThreadLocal<ExtentTest> currentSection = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Long>> sectionStartTimes = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<String> currentSectionName = new ThreadLocal<>();

    // ✅ Start a new section (child node under test)
    public static void startSection(String sectionName) {
        ExtentTest section = ExtentTestManager.getTest().createNode("📂 " + sectionName);
        currentSection.set(section);
        stepCounter.set(1);

        sectionStartTimes.get().put(sectionName, System.currentTimeMillis());
        currentSectionName.set(sectionName);
    }

    // ✅ Log a step with optional screenshot
    public static void logStep(Status status, String message, boolean captureScreenshot) {
        int step = stepCounter.get();
        ExtentTest section = currentSection.get();
        ExtentTest logger = (section != null) ? section : ExtentTestManager.getTest();

        try {
            if (captureScreenshot) {
                String className = ExtentTestManager.getTest().getModel().getName(); // class name
                String stepName = "Step_" + step;

                String screenshotPath = ScreenshotUtil.captureScreenshot(stepName, className);
                if (screenshotPath != null) {
                    logger.log(status, "🟢 Step " + step + ": " + message,
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } else {
                    logger.log(status, "🟢 Step " + step + ": " + message + " (❌ Screenshot not available)");
                }
            } else {
                logger.log(status, "🟢 Step " + step + ": " + message);
            }
        } catch (Exception e) {
            logger.warning("⚠️ Failed to capture screenshot or log step: " + e.getMessage());
        }

        stepCounter.set(step + 1);
    }

    // ✅ Overloaded method with no screenshot (default)
    public static void logStep(Status status, String message) {
        logStep(status, message, false);
    }

    // ✅ Simplified info-level logging
    public static void logStep(String message) {
        logStep(Status.INFO, message, false);
    }

    // ✅ End the current section and print time taken
    public static void endSection() {
        String sectionName = currentSectionName.get();
        if (sectionName == null) return;

        Long startTime = sectionStartTimes.get().get(sectionName);
        if (startTime != null) {
            long duration = (System.currentTimeMillis() - startTime) / 1000;
            ExtentTest section = currentSection.get();
            if (section != null) {
                section.log(Status.INFO, "🕒 Time taken for `" + sectionName + "`: **" + duration + "s**");
            } else {
                ExtentTestManager.getTest().log(Status.INFO, "🕒 Time taken for `" + sectionName + "`: **" + duration + "s**");
            }
        }

        // Cleanup
        stepCounter.set(1);
        currentSection.remove();
        currentSectionName.remove();
        sectionStartTimes.get().remove(sectionName);
    }

    // ✅ Reset everything at test start
    public static void resetSteps() {
        stepCounter.set(1);
        currentSection.remove();
        currentSectionName.remove();
        sectionStartTimes.remove();
    }
}
