package com.akshar.framework.controller;

import com.akshar.framework.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        return ResponseEntity.ok(analyticsService.getSummary());
    }

    @GetMapping("/suite/{suiteId}")
    public ResponseEntity<Map<String, Object>> getSuiteSummary(@PathVariable Long suiteId) {
        return ResponseEntity.ok(analyticsService.getSuiteSummary(suiteId));
    }

    @GetMapping("/top-failures")
    public ResponseEntity<List<Map<String, Object>>> getTopFailingTests() {
        return ResponseEntity.ok(analyticsService.getTopFailingTests());
    }
    @GetMapping("/history")
    public ResponseEntity<List<Map<String, Object>>> getExecutionHistory() {
        return ResponseEntity.ok(analyticsService.getExecutionHistory());
    }
}