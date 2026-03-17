package com.akshar.framework.model;

import jakarta.persistence.*;

@Entity
@Table(name = "test_suites")
public class TestSuite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String suiteName;

    private String description;

    public TestSuite() {
    }

    public TestSuite(String suiteName, String description) {
        this.suiteName = suiteName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}