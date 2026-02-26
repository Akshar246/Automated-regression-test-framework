# Architecture Overview - Milestone 1

**Project:** Automated Regression Test Suite Framework  
**Author:** Akshar Chanchlani
**Milestone:** 1 (Foundation & Design Phase)  
**Date:** 26-02-2026

---

# 1. Purpose

This document describes the high-level architecture defined during Milestone 1.

The objective of this milestone is to establish:
- Project structure
- Technology stack
- Database foundation
- Core entity models
- Clean architectural direction for future modules

No integration engine, scheduling system, reporting, or analytics logic is implemented in this milestone.

---

# 2. Architectural Style

The project follows a **Layered Architecture (Separation of Concerns)**.

Planned layers:

1. **Controller Layer** (Future)
    - Will expose REST APIs via Spring Boot
    - Handles incoming HTTP requests

2. **Service Layer** (Future)
    - Contains business logic
    - Coordinates test execution and result processing

3. **Repository Layer**
    - Responsible for database interaction
    - Uses JDBC or Spring Data (depending on design decision)

4. **Model Layer**
    - Contains entity classes
    - Represents database tables
    - Includes annotations for persistence mapping

Milestone 1 focuses primarily on:
- Model layer
- Database structure
- Basic project configuration

---

# 3. High-Level System Vision (Future Scope)

The complete framework (future milestones) will follow this flow:

User / CI Pipeline  
↓  
Spring Boot REST API  
↓  
Service Layer  
↓  
Test Execution Engine (Selenium / REST-Assured)  
↓  
Database (Store Results)  
↓  
Reporting / Analytics

In Milestone 1, we only prepare the foundational structure required to support this flow.

---

# 4. Technology Stack (Milestone 1)

| Component | Technology |
|-----------|----------|
| Language | Java 17+ |
| Framework | Spring Boot |
| Build Tool | Maven |
| Database | MySQL|
| Version Control | Git |
| IDE | IntelliJ IDEA |

Training technologies introduced in Milestone 1:
- Core Java (OOP)
- JDBC
- Multithreading concepts
- Selenium basics (introductory)
- REST-Assured basics (introductory)

---

# 5. Project Structure

The repository is organized as follows:
automated-regression-test-framework/
├── src/   
│ ├── main/java/com/<package>/    
│ │ ├── model/    
│ │ ├── repository/ (optional)     
│ │ └── config/ (if needed)     
│ └── main/resources/     
├── docs/     
│ └── milestone-1/     
├── scripts/     
│ └── db/     
├── pocs/    
└── README.md


### Explanation:

- **model/** → Contains entity classes representing DB tables
- **repository/** → Handles data persistence logic
- **scripts/db/** → Database schema SQL scripts
- **pocs/** → Small training demos for JDBC, threading, etc.
- **docs/** → Documentation for milestone tracking

---

# 6. Database Architecture

A relational database is used to:
- Store test cases
- Store test suites
- Store execution runs
- Store execution results

Relational DB is chosen because:
- Structured data
- Clear relationships
- Suitable for analytics (future milestone)
- Easy integration with JDBC/Spring Data

---

# 7. Core Entity Model Design (Milestone 1)

The following foundational entities are defined:

## 7.1 TestCase
Represents a single test case.

Possible attributes:
- id
- name
- type (UI / API)
- status
- createdAt

---

## 7.2 TestSuite
Represents a collection of test cases.

Possible attributes:
- id
- suiteName
- description

Relationship:
- One TestSuite → Many TestCases

---

## 7.3 TestRun
Represents a single execution instance of a suite.

Possible attributes:
- id
- suiteId
- executionTime
- status

---

## 7.4 TestResult
Represents the result of a test case inside a run.

Possible attributes:
- id
- testCaseId
- runId
- result (PASS/FAIL)
- duration
- errorMessage

Relationship:
- One TestRun → Many TestResults

---

# 8. Design Principles Followed

- Separation of concerns
- Clean package structure
- Scalability-ready foundation
- Database normalization
- No heavy external dependencies beyond Spring Boot
- Extendable architecture for future modules

---

# 9. Milestone 1 Boundary

This milestone does NOT include:

- Selenium execution engine implementation
- REST API endpoints
- Scheduling logic
- Parallel execution implementation
- Reporting generation
- Analytics dashboards

Those will be developed in later milestones.

Milestone 1 strictly establishes the architectural foundation required for those components.

---

# 10. Future Architectural Expansion (Preview Only)

Future enhancements will include:

- REST Controllers for test management
- Execution orchestration layer
- Thread pool-based parallel execution system
- Reporting module (HTML/CSV/JUnit)
- Analytics tracker using stored test results

These components will build upon the foundation defined in this milestone.

---

# 11. Conclusion

Milestone 1 establishes a scalable and maintainable architectural foundation for the Automated Regression Test Suite Framework.

The project is now prepared for:
- Integration engine implementation (Milestone 2)
- Scheduling and reporting modules (Milestone 3)
- Analytics and deployment (Milestone 4)

The focus of this phase has been correctness of setup, clarity of design, and proper definition of core entity models.