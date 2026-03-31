package com.akshar.framework.service;

import com.akshar.framework.model.TestResult;
import com.akshar.framework.model.TestRun;
import com.akshar.framework.model.TestStatus;
import com.akshar.framework.repository.TestResultRepository;
import com.akshar.framework.repository.TestRunRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final TestResultRepository testResultRepository;
    private final TestRunRepository testRunRepository;

    public AnalyticsService(TestResultRepository testResultRepository,
                            TestRunRepository testRunRepository) {
        this.testResultRepository = testResultRepository;
        this.testRunRepository = testRunRepository;
    }

    public Map<String, Object> getSummary() {
        long totalRuns = testResultRepository.count();
        long passed = testResultRepository.countByStatus(TestStatus.PASS);
        long failed = testResultRepository.countByStatus(TestStatus.FAIL);

        double passPercentage = 0.0;
        if (totalRuns > 0) {
            passPercentage = ((double) passed / totalRuns) * 100;
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRuns", totalRuns);
        summary.put("passed", passed);
        summary.put("failed", failed);
        summary.put("passPercentage", Math.round(passPercentage * 100.0) / 100.0);

        return summary;
    }

    public Map<String, Object> getSuiteSummary(Long suiteId) {
        List<TestRun> suiteRuns = testRunRepository.findByTestCaseTestSuiteId(suiteId);

        long totalRuns = suiteRuns.size();
        long passed = suiteRuns.stream().filter(run -> run.getStatus() == TestStatus.PASS).count();
        long failed = suiteRuns.stream().filter(run -> run.getStatus() == TestStatus.FAIL).count();

        double passPercentage = 0.0;
        if (totalRuns > 0) {
            passPercentage = ((double) passed / totalRuns) * 100;
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("suiteId", suiteId);
        summary.put("totalRuns", totalRuns);
        summary.put("passed", passed);
        summary.put("failed", failed);
        summary.put("passPercentage", Math.round(passPercentage * 100.0) / 100.0);

        return summary;
    }

    public List<Map<String, Object>> getTopFailingTests() {
        List<Object[]> rows = testRunRepository.findTopTestsByStatus(TestStatus.FAIL);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> item = new HashMap<>();
            item.put("testName", row[0]);
            item.put("failureCount", row[1]);
            result.add(item);
        }

        return result;
    }

    public List<Map<String, Object>> getExecutionHistory() {
        List<TestResult> recentResults =
                testResultRepository.findAllByOrderByIdDesc(PageRequest.of(0, 10));

        List<Map<String, Object>> history = new ArrayList<>();

        for (TestResult result : recentResults) {
            Map<String, Object> item = new HashMap<>();
            item.put("testName", result.getTestRun().getTestCase().getTestName());
            item.put("status", result.getStatus().name());
            item.put("duration", result.getDurationInMs());
            item.put("error", result.getErrorMessage());
            item.put("time", result.getTestRun().getStartTime());

            history.add(item);
        }

        return history;
    }

    public Map<String, Object> getPerformanceMetrics() {
        Double average = testResultRepository.findAverageExecutionTime();
        Long fastest = testResultRepository.findFastestExecutionTime();
        Long slowest = testResultRepository.findSlowestExecutionTime();

        Map<String, Object> performance = new HashMap<>();
        performance.put("averageExecutionTime", average == null ? 0.0 : Math.round(average * 100.0) / 100.0);
        performance.put("fastestExecutionTime", fastest == null ? 0 : fastest);
        performance.put("slowestExecutionTime", slowest == null ? 0 : slowest);

        return performance;
    }
}