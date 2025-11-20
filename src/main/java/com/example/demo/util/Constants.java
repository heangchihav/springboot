package com.example.demo.util;

/**
 * Application-wide constants
 */
public class Constants {
    
    // API Endpoints
    public static final String API_BASE_PATH = "/api";
    
    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    
    // Date Formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private Constants() {
        // Utility class - prevent instantiation
    }
}

