package com.akshar.framework.repository;

import com.akshar.framework.model.TestRun;
import com.akshar.framework.model.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestRunRepository extends JpaRepository<TestRun, Long> {

    List<TestRun> findByTestCaseTestSuiteId(Long suiteId);

    @Query("""
        SELECT tr.testCase.testName, COUNT(tr)
        FROM TestRun tr
        WHERE tr.status = :status
        GROUP BY tr.testCase.testName
        ORDER BY COUNT(tr) DESC
    """)
    List<Object[]> findTopTestsByStatus(TestStatus status);
}