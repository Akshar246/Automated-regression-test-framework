# Requirements - Milestone 1 (Weeks 1–2)
**Project:** Automated Regression Test Suite Framework (Foundation)  
**Author:** Akshar Chanchlani  
**Date:** 26-02-2026  
**Milestone:** 1 (Environment + Training + Entity Models)

---

## 1. Purpose
This document captures the requirements for **Milestone 1**, focusing on setting up the development environment, initializing the Spring Boot project, configuring the database, completing initial training activities, and defining core test management **entity models**.

> Note: This document intentionally covers **Milestone 1 only**.

---

## 2. Problem Statement
We need a scalable foundation for an automated regression testing framework that will later support:
- Web UI testing (Selenium)
- API testing (REST-Assured)
- Parallel execution (multithreading)
- Persistent storage for execution results (JDBC)
- Spring Boot REST APIs for test management

Milestone 1 creates the base project structure and data layer foundation (entities + DB setup) to support later modules.

---

## 3. Milestone 1 Objectives
By the end of Milestone 1, the project should have:
1. Development environment set up (JDK, IDE, Git, DB).
2. Spring Boot project initialized using Maven/Gradle and runnable locally.
3. Database configured and schema prepared for core entities.
4. Initial training completed (OOP, JDBC, Selenium/REST-Assured basics, parallel execution concepts).
5. Core **entity models defined with annotations** as the foundation for future persistence and APIs.

---

## 4. Scope

### 4.1 In Scope (Milestone 1)
**Environment & Tools**
- Install and configure JDK (recommended Java 17+).
- IDE setup (IntelliJ IDEA) and project import.
- Git repository initialized and structured professionally.

**Project Initialization**
- Spring Boot project created using Maven/Gradle.
- Basic application runs locally (no advanced features required yet).

**Database Setup**
- Choose and install a relational database (e.g., MySQL/PostgreSQL).
- Configure connection in Spring Boot.
- Provide database schema scripts for initial tables.

**Entity Models**
- Define the primary test management entities (with annotations).
- Identify entity relationships and core fields.

**Training Evidence (Lightweight)**
- Document learning outcomes and/or include small POCs (optional but recommended):
    - JDBC connectivity demo
    - Multithreading demo
    - Simple Selenium/REST-Assured practice snippet

### 4.2 Out of Scope (Not for Milestone 1)
These will be implemented in later milestones (so avoid building them now):
- Full Selenium/REST-Assured integration engine
- REST APIs for managing tests/runs
- Scheduling engine and parallel execution system
- Reporting generation (HTML/CSV/JUnit) and log collection
- Analytics/trend dashboards

---

## 5. Stakeholders
- **Intern (Developer):** Akshar Chanchlani
- **Mentor/Reviewer:** Joycee 
- **Team/Organization:** Infosys

---

## 6. Functional Requirements (Milestone 1)

### FR-01 — Environment Setup
The system shall have a working local development environment:
- JDK installed and verified (`java -version`).
- IntelliJ configured and able to run the Spring Boot project.
- Git configured for version control.
- Database installed and reachable locally.

**Acceptance:** Developer can build and run the application and connect to DB.

---

### FR-02 — Spring Boot Project Initialization
The system shall provide a Spring Boot project skeleton with:
- Standard package structure (e.g., `config/`, `model/`, etc.)
- Build tool configuration (Maven `pom.xml` or Gradle `build.gradle`)
- A runnable `main` application class

**Acceptance:** `mvn spring-boot:run` (or Gradle equivalent) starts successfully.

---

### FR-03 — Database Configuration & Schema
The system shall include:
- DB connection configuration using Spring Boot config files
- SQL scripts to create schema for initial entities (recommended: `schema.sql`)

**Acceptance:** Schema can be created locally and verified by viewing tables in DB.

---

### FR-04 — Core Entity Models with Annotations
The system shall define entity models required for test management and result storage.
Minimum recommended entities:
- `TestCase`
- `TestSuite`
- `TestRun`
- `TestResult`

Each entity should include:
- Unique identifier field (`id`)
- Core attributes relevant to test tracking
- Relationships (e.g., suite-to-testcases, run-to-results) where applicable
- Appropriate annotations (e.g., JPA annotations if using ORM)

**Acceptance:** Entity classes compile successfully and reflect the intended data model.

---

### FR-05 — Requirements & Documentation (Milestone 1)
The repository shall include Milestone 1 documentation:
- `requirements.md` (this file)
- `architecture.md` (high-level plan)
- `data-model.md` (tables + relationships)
- `weekly-log.md` (Week 1–2 progress)

**Acceptance:** Docs are present, readable, and match Milestone 1 scope.

---

### FR-06 — Training Proof / POCs (Recommended)
The repository should include lightweight proof of training completion:
- A simple JDBC proof (connect/query)
- A simple multithreading proof (thread pool / parallel concept)
- Optional: tiny Selenium and REST-Assured practice examples

**Acceptance:** POCs compile/run OR training summary is documented in weekly log.

---

## 7. Non-Functional Requirements (Milestone 1)

### NFR-01 — Reproducibility
Another developer should be able to run the project by following README steps.

### NFR-02 — Clean Repository
Repo should not include:
- build output folders (`target/`, `build/`)
- IDE files (`.idea/`, `*.iml`)
- secrets/credentials

### NFR-03 — Security (Local)
No passwords/tokens should be committed.
Use `.example` configs or environment variables for secrets.

### NFR-04 — Maintainable Structure
Project should follow a clear folder structure:
- `src/` for application code
- `docs/` for documentation
- `scripts/` for DB scripts
- `pocs/` for learning proofs (optional)

---

## 8. Deliverables (Milestone 1)
- Spring Boot project initialized + runnable
- DB configuration + schema scripts
- Entity models with annotations
- Documentation package (Milestone 1)
- Optional POCs for training evidence
- README with setup/run instructions

---

## 9. Milestone 1 Acceptance Criteria (Checklist)
Milestone 1 is complete when:
- [ ] JDK, IDE, DB installed and verified locally
- [ ] Spring Boot project initialized and runs
- [ ] DB configured and schema scripts included
- [ ] Training topics covered (documented + optional POCs)
- [ ] Entity models defined with annotations
- [ ] Milestone 1 docs added (`requirements`, `architecture`, `data-model`, `weekly-log`)

---

## 10. Assumptions & Constraints
- The framework should be designed to be scalable and modular.
- Avoid AI or heavy dependencies beyond Spring Boot.
- Use core Java concepts (OOP, JDBC, multithreading) as required.

---

## 11. Open Questions (To Confirm with Mentor)
1. Which DB should we standardize on (MySQL vs PostgreSQL)?
2. Should the repo be private/public? Any policy restrictions?
3. Should entity models be JPA-based or plain POJOs + JDBC mapping?
4. What reporting formats are preferred later (HTML/CSV/JUnit all required or optional)?

---

## 12. Glossary
- **POC:** Proof of Concept (small demo to prove learning)
- **Entity Model:** Class representing DB table structure and relationships
- **TestRun:** One execution instance of a test suite
- **TestResult:** Outcome of a test case execution inside a run