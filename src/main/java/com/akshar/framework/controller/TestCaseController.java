package com.akshar.framework.controller;

import com.akshar.framework.model.TestCase;          // ✅ add
import com.akshar.framework.repository.TestCaseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testcases")
public class TestCaseController {

    private final TestCaseRepository repo;

    public TestCaseController(TestCaseRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<TestCase> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public TestCase create(@RequestBody TestCase testCase) {
        return repo.save(testCase);
    }
}