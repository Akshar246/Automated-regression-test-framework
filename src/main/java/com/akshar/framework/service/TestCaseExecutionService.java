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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Gets all test cases in one suits
    public Map<String, Object> executeTestSuite(Long suiteId) {
        List<TestCase> testCases = testCaseRepository.findByTestSuiteId(suiteId);

        if (testCases.isEmpty()) {
            throw new ResourceNotFoundException("No test cases found for suite id: " + suiteId);
        }

        int passed = 0;
        int failed = 0;

        for (TestCase testCase : testCases) {
            if (!Boolean.TRUE.equals(testCase.getActive())) {
                continue;
            }

            TestResult result = executeTestCase(testCase.getId());

            if (result.getStatus() == TestStatus.PASS) {
                passed++;
            } else {
                failed++;
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("suiteId", suiteId);
        summary.put("total", testCases.size());
        summary.put("passed", passed);
        summary.put("failed", failed);

        return summary;
    }
}