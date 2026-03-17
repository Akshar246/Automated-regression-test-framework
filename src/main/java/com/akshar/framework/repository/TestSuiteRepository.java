package com.akshar.framework.repository;

import com.akshar.framework.model.TestSuite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSuiteRepository extends JpaRepository<TestSuite, Long> {
}