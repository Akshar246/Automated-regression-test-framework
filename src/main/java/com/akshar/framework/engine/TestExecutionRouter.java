package com.akshar.framework.engine;

import com.akshar.framework.model.TestCase;
import com.akshar.framework.model.TestResult;
import com.akshar.framework.model.TestRun;
import com.akshar.framework.model.TestType;
import org.springframework.stereotype.Component;

@Component
public class TestExecutionRouter {

    private final SeleniumExecutor seleniumExecutor;
    private final ApiExecutor apiExecutor;

    public TestExecutionRouter(SeleniumExecutor seleniumExecutor, ApiExecutor apiExecutor) {
        this.seleniumExecutor = seleniumExecutor;
        this.apiExecutor = apiExecutor;
    }

    public TestResult execute(TestCase testCase, TestRun testRun) {
        if (testCase.getTestType() == TestType.UI) {
            return seleniumExecutor.execute(testCase, testRun);
        } else if (testCase.getTestType() == TestType.API) {
            return apiExecutor.execute(testCase, testRun);
        }

        throw new IllegalArgumentException("Unsupported test type: " + testCase.getTestType());
    }
}