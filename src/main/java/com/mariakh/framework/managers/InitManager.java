package com.mariakh.framework.managers;

import java.time.Duration;

public class InitManager {
    private static final DriverManager driverManager = DriverManager.getInstance();

    public static void initFramework() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(5));
        driverManager.getDriver().manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(15));
    }

    public static void quitFramework() {
        driverManager.quitDriver();
    }
}
