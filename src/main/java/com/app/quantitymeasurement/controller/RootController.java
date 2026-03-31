package com.app.quantitymeasurement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, String> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Quantity Measurement API is running");
        response.put("swagger", "/swagger-ui.html");
        response.put("api-docs", "/api-docs");
        return response;
    }
}
