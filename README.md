# Automated Regression Test Framework

A database-driven automation framework built using Java and Spring Boot that supports both API and UI test execution with centralized result tracking and reporting.

---

## Overview

This project is designed to simulate a real-world test automation framework where test cases are managed in a database and executed dynamically. The framework supports both API and UI testing, enabling flexible and scalable test execution without hardcoding test logic.

Instead of executing tests individually, the framework introduces **test suite execution**, allowing multiple test cases to be triggered in a single run, similar to how enterprise automation pipelines operate.

---

## Key Features

* API test execution with status code and response validation
* UI test execution using Selenium WebDriver
* Automatic screenshot capture on UI test failure
* Database-driven test management (TestCase, TestRun, TestResult)
* Test suite execution (run multiple test cases together)
* Execution summary with pass/fail reporting
* Clean separation of execution logic using routing pattern
* Centralized error handling and result persistence

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA (Hibernate)
* MySQL
* Selenium WebDriver
* Maven

---

## How It Works

1. Test cases are created and stored in the database
2. Each test case defines:

   * API endpoint or UI URL
   * expected validations
3. The execution engine dynamically routes the test:

   * API → HttpClient
   * UI → Selenium
4. Results are stored in:

   * `test_runs`
   * `test_results`
5. Test suites allow grouped execution of multiple test cases

---

## Running the Application

### Prerequisites

* Java 17
* Maven
* MySQL running locally

### Setup

1. Create database:

```sql
CREATE DATABASE test_framework;
```

2. Configure database in `application.properties`

3. Run the application:

```bash
mvn spring-boot:run
```

---

## API Endpoints

### Create Test Case

```
POST /tests
```

### Run Single Test Case

```
POST /tests/run/{id}
```

### Run Test Suite

```
POST /tests/suite/run/{suiteId}
```

---

## Sample Test Case (API)

```json
{
  "testName": "Users API Test",
  "testType": "API",
  "endpoint": "https://jsonplaceholder.typicode.com/users",
  "httpMethod": "GET",
  "expectedStatusCode": 200,
  "expectedResponseText": "Leanne Graham"
}
```

---

## Sample Test Case (UI)

```json
{
  "testName": "UI Title Validation",
  "testType": "UI",
  "uiTargetUrl": "https://example.com",
  "expectedPageTitle": "Example Domain"
}
```

---

## Project Structure

```
engine/        → Execution logic (API + UI)
service/       → Business logic
repository/    → Database access
model/         → Entities
controller/    → REST APIs
utils/         → Helpers (Screenshot, JSON, Date)
config/        → Driver and framework configuration
```

---

## Milestone Progress

### Milestone 1

* Project setup and database configuration
* Entity modeling and architecture design

### Milestone 2

* API execution engine
* Result tracking and persistence

### Milestone 3

* UI test execution with Selenium
* Screenshot capture on failure
* Test suite execution
* Execution reporting

---

## Why This Project Stands Out

This framework is not limited to running tests—it focuses on **scalability and real-world design principles**:

* Decoupled execution using routing pattern
* Database-driven test configuration
* Supports both API and UI testing in a single framework
* Introduces suite-level execution similar to CI/CD pipelines

---

## Future Enhancements

* Parallel test execution
* Scheduled test runs
* Web-based dashboard for reporting
* CI/CD integration (GitHub Actions / Jenkins)

---

## Author

Akshar
Automated Testing Framework Project
Infosys Virtual Internship (6.0)
