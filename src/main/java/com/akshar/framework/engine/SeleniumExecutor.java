package com.akshar.framework.engine;

import com.akshar.framework.config.DriverFactory;
import com.akshar.framework.model.TestCase;
import com.akshar.framework.model.TestResult;
import com.akshar.framework.model.TestRun;
import com.akshar.framework.model.TestStatus;
import com.akshar.framework.util.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class SeleniumExecutor implements TestExecutor {

    @Override
    public TestResult execute(TestCase testCase, TestRun testRun) {
        long start = System.currentTimeMillis();
        TestResult result = new TestResult();
        result.setTestRun(testRun);

        try {
            if (testCase.getUiTargetUrl() == null || testCase.getUiTargetUrl().isBlank()) {
                throw new IllegalArgumentException("UI target URL is missing for test case: " + testCase.getTestName());
            }

            DriverFactory.initDriver();
            WebDriver driver = DriverFactory.getDriver();
            driver.get(testCase.getUiTargetUrl());

            String actualTitle = driver.getTitle();
            Thread.sleep(3000);
            String expectedTitle = testCase.getExpectedPageTitle();

            if (expectedTitle != null && expectedTitle.equals(actualTitle)) {
                result.setStatus(TestStatus.PASS);
                result.setActualResult("UI validation passed. Page title matched: " + actualTitle);
            } else {
                result.setStatus(TestStatus.FAIL);
                result.setActualResult("UI validation failed. Actual title: " + actualTitle);
                result.setErrorMessage("Expected title: " + expectedTitle + ", but found: " + actualTitle);
                result.setScreenshotPath(ScreenshotUtil.captureScreenshot(driver, testCase.getTestName()));
            }

        } catch (Exception ex) {
            result.setStatus(TestStatus.FAIL);
            result.setErrorMessage(ex.getMessage());
            WebDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                result.setScreenshotPath(ScreenshotUtil.captureScreenshot(driver, testCase.getTestName()));
            }
        } finally {
            result.setDurationInMs(System.currentTimeMillis() - start);
            DriverFactory.quitDriver();
        }

        return result;
    }
}