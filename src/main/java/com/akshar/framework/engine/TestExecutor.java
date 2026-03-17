package com.akshar.framework.engine;

import com.akshar.framework.model.TestCase;
import com.akshar.framework.model.TestResult;
import com.akshar.framework.model.TestRun;

public interface TestExecutor {
    TestResult execute(TestCase testCase, TestRun testRun);
}