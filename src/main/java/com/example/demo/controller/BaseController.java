package com.example.demo.controller;

import com.example.demo.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Base controller for common endpoints
 */
@RestController
@RequestMapping("/api")
public class BaseController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Application is running");
    }

    @GetMapping("/health/db")
    public ResponseEntity<ApiResponse<Map<String, Object>>> databaseHealth() {
        Map<String, Object> dbInfo = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            dbInfo.put("status", "CONNECTED");
            dbInfo.put("databaseProductName", metaData.getDatabaseProductName());
            dbInfo.put("databaseProductVersion", metaData.getDatabaseProductVersion());
            dbInfo.put("driverName", metaData.getDriverName());
            dbInfo.put("driverVersion", metaData.getDriverVersion());
            dbInfo.put("url", metaData.getURL());
            dbInfo.put("username", metaData.getUserName());
            dbInfo.put("readOnly", metaData.isReadOnly());

            // Test query
            connection.createStatement().executeQuery("SELECT 1");
            dbInfo.put("testQuery", "SUCCESS");

            return ResponseEntity.ok(ApiResponse.success("Database connection successful", dbInfo));

        } catch (SQLException e) {
            dbInfo.put("status", "FAILED");
            dbInfo.put("error", e.getMessage());
            return ResponseEntity.status(503)
                    .body(ApiResponse.error("Database connection failed: " + e.getMessage()));
        }
    }
}
