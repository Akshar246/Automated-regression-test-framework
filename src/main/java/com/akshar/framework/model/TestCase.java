package com.akshar.framework.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_cases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestType type; // UI / API

    // this maps to your executor class key later, e.g. "ui.google.title"
    @Column(nullable = false)
    private String executorKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suite_id", nullable = false)
    private TestSuite suite;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public TestType getType() { return type; }
    public void setType(TestType type) { this.type = type; }
    public String getExecutorKey() { return executorKey; }
    public void setExecutorKey(String executorKey) { this.executorKey = executorKey; }
    public TestSuite getSuite() { return suite; }
    public void setSuite(TestSuite suite) { this.suite = suite; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}