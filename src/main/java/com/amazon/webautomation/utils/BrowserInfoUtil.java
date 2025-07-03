package com.amazon.webautomation.utils;

import com.amazon.webautomation.config.ConfigReader;

public class BrowserInfoUtil {
    public static String getDeviceName() {
        String browser = ConfigReader.get("browser");
        boolean headless = ConfigReader.getBoolean("headless");
        return headless ? browser + "-Headless" : browser;
    }
}
