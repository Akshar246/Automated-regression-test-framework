package com.akshar.framework.repository;

import com.akshar.framework.model.TestResult;
import com.akshar.framework.model.TestStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    long countByStatus(TestStatus status);

    List<TestResult> findAllByOrderByIdDesc(Pageable pageable);
}