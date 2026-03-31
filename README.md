## Automated Regression Test Framework

A scalable, database-driven automation framework designed to execute API and UI tests with parallel processing, failure diagnostics, and built-in analytics.

Designed to simulate real-world automation systems used in CI/CD environments.

---

## Overview

This project implements a unified test execution system where test cases are managed through a database and executed via a modular engine. It supports both API and UI testing, enabling efficient regression testing while providing actionable insights into test performance and reliability.

---

## Key Highlights

- Built a database-driven automation framework instead of script-based testing  
- Implemented parallel execution using multithreading for faster test runs  
- Designed a modular execution engine supporting both API and UI testing  
- Added analytics layer for performance tracking and failure insights  

---

## What This System Solves

- Eliminates repetitive manual testing  
- Enables running multiple test cases together using suites  
- Reduces execution time through parallel processing  
- Provides insights into failures and performance  
- Centralizes test execution and reporting  

---

## What This System Does

### Test Execution
- Execute individual test cases via API  
- Execute complete test suites  
- Parallel execution using multithreading (ExecutorService)  

### API Testing
- Supports HTTP methods (GET, POST, PUT, DELETE, PATCH)  
- Status code validation  
- Response content validation  

### UI Testing
- Selenium WebDriver integration  
- Page title validation  
- Automatic screenshot capture on failure  

### Reporting and Analytics
- Overall execution summary  
- Suite-level performance metrics  
- Identification of frequently failing tests  
- Execution history tracking  
- Performance metrics (average, fastest, slowest execution time)  

---

## Architecture

Controller → Service → Execution Engine → Database

- Controller: Exposes REST endpoints  
- Service Layer: Handles business logic  
- Execution Engine: Routes execution to API or UI handlers  
- Database: Stores test cases, runs, and results  

---

## Technology Stack

- Java 17  
- Spring Boot  
- MySQL  
- Selenium WebDriver  
- Maven  

---

## Core APIs

### Test Execution

Run a single test case:
POST /tests/run/{id}

Run a test suite (parallel execution):
POST /tests/suite/run/{suiteId}

---

### Analytics

GET /analytics/summary  
GET /analytics/suite/{suiteId}  
GET /analytics/top-failures  
GET /analytics/history  
GET /analytics/performance  

---

## Data Model

- TestCase – Defines API/UI test configuration  
- TestRun – Tracks each execution instance  
- TestResult – Stores execution outcome, duration, and errors  

---

## Execution Flow

1. Test cases are created and stored in the database  
2. Execution is triggered via API  
3. Engine routes execution to API or UI executor  
4. Results are stored in the database  
5. Analytics APIs provide insights into execution trends  

---

## Sample Output

### Suite Execution Result

{
  "suiteId": 1,
  "total": 4,
  "executionMode": "PARALLEL",
  "passed": 2,
  "failed": 2
}

### Analytics Summary

{
  "totalRuns": 4,
  "passed": 2,
  "failed": 2,
  "passPercentage": 50.0
}

---

## Demo Screenshots

### 1. Suite Execution (Parallel Run)
Suite Execution <img width="1428" height="848" alt="Suite-run" src="https://github.com/user-attachments/assets/32cf91ea-cec8-477a-83a6-f2964ce6ea27" />



### 2. UI Failure with Screenshot Capture
UI Failure <img width="3024" height="1476" alt="UI-Failure" src="https://github.com/user-attachments/assets/82d3e52b-6840-46ba-95dc-5d5abbc041c9" />


### 3. Analytics Summary API Response
Analytics Summary <img width="1434" height="848" alt="Analytics-summary" src="https://github.com/user-attachments/assets/81f82a7e-120e-496e-91c0-2c5912895b45" />


---

## How to Run

1. Clone the repository  
git clone (https://github.com/Akshar246/Automated-regression-test-framework.git)

2. Configure MySQL database  

3. Run the application  
mvn spring-boot:run  

4. Use APIs via Postman:
- POST /tests/run/{id}  
- POST /tests/suite/run/{suiteId}  
- GET /analytics/summary  

---

## Real-World Relevance

This framework simulates real automation systems used in CI/CD pipelines by:
- executing regression suites in parallel  
- tracking failures and performance metrics  
- providing analytics for decision-making  

---

## Why This Project Stands Out

- Database-driven test orchestration instead of static scripts  
- Parallel execution for faster regression cycles  
- Integrated analytics layer (execution + insights)  
- Clean layered architecture for scalability  
- Supports both API and UI testing in a unified system  

---

## Potential Enhancements

- Dashboard for analytics visualization  
- CI/CD integration  
- Scheduled test execution  
- Exportable reports (PDF/CSV)  

---

## Author
Akshar,
Developed as part of an automation framework project focused on building a production-style testing system.
