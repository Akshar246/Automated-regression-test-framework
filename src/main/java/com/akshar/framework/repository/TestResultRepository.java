package com.akshar.framework.repository;

import com.akshar.framework.model.TestResult;
import com.akshar.framework.model.TestStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    long countByStatus(TestStatus status);

    List<TestResult> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT AVG(tr.durationInMs) FROM TestResult tr")
    Double findAverageExecutionTime();

    @Query("SELECT MIN(tr.durationInMs) FROM TestResult tr")
    Long findFastestExecutionTime();

    @Query("SELECT MAX(tr.durationInMs) FROM TestResult tr")
    Long findSlowestExecutionTime();
}