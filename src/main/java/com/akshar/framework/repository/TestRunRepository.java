package com.akshar.framework.repository;

import com.akshar.framework.model.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRunRepository extends JpaRepository<TestRun, Long> {
}