package com.akshar.framework.service;

import com.akshar.framework.engine.TestExecutionRouter;
import com.akshar.framework.exception.ResourceNotFoundException;
import com.akshar.framework.model.TestCase;
import com.akshar.framework.model.TestResult;
import com.akshar.framework.model.TestRun;
import com.akshar.framework.model.TestStatus;
import com.akshar.framework.repository.TestCaseRepository;
import com.akshar.framework.repository.TestResultRepository;
import com.akshar.framework.repository.TestRunRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class TestCaseExecutionService {

    private final TestCaseRepository testCaseRepository;
    private final TestRunRepository testRunRepository;
    private final TestResultRepository testResultRepository;
    private final TestExecutionRouter testExecutionRouter;

    public TestCaseExecutionService(TestCaseRepository testCaseRepository,
                                    TestRunRepository testRunRepository,
                                    TestResultRepository testResultRepository,
                                    TestExecutionRouter testExecutionRouter) {
        this.testCaseRepository = testCaseRepository;
        this.testRunRepository = testRunRepository;
        this.testResultRepository = testResultRepository;
        this.testExecutionRouter = testExecutionRouter;
    }

    public TestResult executeTestCase(Long testCaseId) {
        TestCase testCase = testCaseRepository.findById(testCaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Test case not found with id: " + testCaseId));

        if (!Boolean.TRUE.equals(testCase.getActive())) {
            throw new IllegalArgumentException("Cannot execute inactive test case.");
        }

        testCase.setStatus(TestStatus.RUNNING);
        testCaseRepository.save(testCase);

        TestRun testRun = new TestRun();
        testRun.setTestCase(testCase);
        testRun.setStartTime(LocalDateTime.now());
        testRun.setStatus(TestStatus.RUNNING);
        testRun = testRunRepository.save(testRun);

        TestResult result;

        try {
            result = testExecutionRouter.execute(testCase, testRun);
            testRun.setEndTime(LocalDateTime.now());
            testRun.setStatus(result.getStatus());
            testCase.setStatus(result.getStatus());

        } catch (Exception ex) {
            result = new TestResult();
            result.setTestRun(testRun);
            result.setStatus(TestStatus.FAIL);
            result.setActualResult("Test execution failed");
            result.setErrorMessage(ex.getMessage());
            result.setDurationInMs(0L);

            testRun.setEndTime(LocalDateTime.now());
            testRun.setStatus(TestStatus.FAIL);
            testCase.setStatus(TestStatus.FAIL);
        }

        testRunRepository.save(testRun);
        testCaseRepository.save(testCase);

        return testResultRepository.save(result);
    }

    public Map<String, Object> executeTestSuite(Long suiteId) {
        List<TestCase> testCases = testCaseRepository.findByTestSuiteId(suiteId);

        if (testCases.isEmpty()) {
            throw new ResourceNotFoundException("No test cases found for suite id: " + suiteId);
        }

        List<TestCase> activeTests = testCases.stream()
                .filter(testCase -> Boolean.TRUE.equals(testCase.getActive()))
                .toList();

        if (activeTests.isEmpty()) {
            throw new ResourceNotFoundException("No active test cases found for suite id: " + suiteId);
        }

        int poolSize = Math.min(activeTests.size(), 5);
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        List<Future<TestResult>> futures = new ArrayList<>();
        List<TestCase> submittedTests = new ArrayList<>();

        for (TestCase testCase : activeTests) {
            submittedTests.add(testCase);
            futures.add(executorService.submit(() -> executeTestCase(testCase.getId())));
        }

        int passed = 0;
        int failed = 0;
        List<Map<String, Object>> results = new ArrayList<>();

        for (int i = 0; i < futures.size(); i++) {
            TestCase testCase = submittedTests.get(i);

            try {
                TestResult result = futures.get(i).get();

                Map<String, Object> testResultMap = new HashMap<>();
                testResultMap.put("testName", testCase.getTestName());
                testResultMap.put("status", result.getStatus().name());
                testResultMap.put("duration", result.getDurationInMs());
                testResultMap.put("error", result.getErrorMessage());

                results.add(testResultMap);

                if (result.getStatus() == TestStatus.PASS) {
                    passed++;
                } else {
                    failed++;
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                Map<String, Object> testResultMap = new HashMap<>();
                testResultMap.put("testName", testCase.getTestName());
                testResultMap.put("status", TestStatus.FAIL.name());
                testResultMap.put("duration", 0);
                testResultMap.put("error", "Execution interrupted");

                results.add(testResultMap);
                failed++;

            } catch (ExecutionException e) {
                Map<String, Object> testResultMap = new HashMap<>();
                testResultMap.put("testName", testCase.getTestName());
                testResultMap.put("status", TestStatus.FAIL.name());
                testResultMap.put("duration", 0);
                testResultMap.put("error", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());

                results.add(testResultMap);
                failed++;
            }
        }

        executorService.shutdown();

        Map<String, Object> summary = new HashMap<>();
        summary.put("suiteId", suiteId);
        summary.put("total", activeTests.size());
        summary.put("passed", passed);
        summary.put("failed", failed);
        summary.put("results", results);
        summary.put("executionMode", "PARALLEL");

        return summary;
    }
}