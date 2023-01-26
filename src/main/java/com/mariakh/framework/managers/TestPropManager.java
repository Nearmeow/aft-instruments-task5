package com.mariakh.framework.managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropManager {

    private final Properties appProperties = new Properties();

    private static TestPropManager instance;

    private TestPropManager() {
        loadApplicationProperties();
    }

    public static TestPropManager getInstance() {
        if (instance == null) {
            instance = new TestPropManager();
        }
        return instance;
    }

    public String getProperty(String key, String defaultValue) {
        return appProperties.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return appProperties.getProperty(key);
    }

    private void loadApplicationProperties() {
        String fileName = System.getProperty("propFile", "app");
        try {
            appProperties.load(new FileInputStream("src/main/resources/" + fileName + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
