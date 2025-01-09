package com.epam.ai.demo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find config.properties");
            }
            // Load the properties file
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load properties", ex);
        }
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, System.getProperty(key, defaultValue));
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }
}
