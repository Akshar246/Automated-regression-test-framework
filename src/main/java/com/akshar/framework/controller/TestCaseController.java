package com.akshar.framework.controller;

import com.akshar.framework.model.TestCase;
import com.akshar.framework.model.TestResult;
import com.akshar.framework.service.TestCaseExecutionService;
import com.akshar.framework.service.TestCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tests")
public class TestCaseController {

    private final TestCaseService testCaseService;
    private final TestCaseExecutionService testCaseExecutionService;

    public TestCaseController(TestCaseService testCaseService,
                              TestCaseExecutionService testCaseExecutionService) {
        this.testCaseService = testCaseService;
        this.testCaseExecutionService = testCaseExecutionService;
    }

    @PostMapping
    public ResponseEntity<TestCase> createTestCase(@RequestBody TestCase testCase) {
        return new ResponseEntity<>(testCaseService.createTestCase(testCase), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCase> updateTestCase(@PathVariable Long id,
                                                   @RequestBody TestCase testCase) {
        return ResponseEntity.ok(testCaseService.updateTestCase(id, testCase));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable Long id) {
        return ResponseEntity.ok(testCaseService.getTestCaseById(id));
    }

    @GetMapping
    public ResponseEntity<List<TestCase>> getAllTestCases() {
        return ResponseEntity.ok(testCaseService.getAllTestCases());
    }

    @GetMapping("/active")
    public ResponseEntity<List<TestCase>> getAllActiveTestCases() {
        return ResponseEntity.ok(testCaseService.getAllActiveTestCases());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestCase(@PathVariable Long id) {
        testCaseService.deleteTestCase(id);
        return ResponseEntity.ok("Test case deleted successfully.");
    }

    @PostMapping("/run/{id}")
    public ResponseEntity<TestResult> executeTestCase(@PathVariable Long id) {
        return ResponseEntity.ok(testCaseExecutionService.executeTestCase(id));
    }

    @PostMapping("/suite/run/{suiteId}")
    public ResponseEntity<Map<String, Object>> executeTestSuite(@PathVariable Long suiteId) {
        return ResponseEntity.ok(testCaseExecutionService.executeTestSuite(suiteId));
    }
}