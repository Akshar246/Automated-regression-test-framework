# Weekly Log - Milestone 1 (Weeks 1–2)

**Project:** Automated Regression Test Suite Framework  
**Author:** Akshar Chanchlani   
**Milestone:** 1 (Introduction & Initial Training)  
**Period:** Week 1–Week 2  
**Date Updated:** 26-02-2026

---

## 1. Milestone 1 Goal (Summary)

Milestone 1 focus:
- Environment setup (JDK, IDE, Git, DB)
- Spring Boot project initialization (Maven)
- Initial training: OOP, JDBC, Selenium/REST-Assured basics, multithreading concepts
- Requirements gathering + architecture planning
- Define core entity models (with annotations)

---

## 2. Week 1 Log - Setup + Foundation

### ✅ Tasks Completed
- Installed and verified Java JDK (recommended Java 17+)
    - Verified using: `java -version`
- Installed and configured IntelliJ IDEA
- Created a GitHub repository (private by default)
- Initialized Spring Boot project using <Maven>
    - Confirmed application runs locally
- Installed and configured database: <MySQL>
    - Confirmed DB connection access locally (client/tool used: <Workbench/pgAdmin/CLI>)
- Structured repository folders:
    - `src/` for application code
    - `docs/milestone-1/` for documentation
    - `scripts/db/` for schema scripts
    - `pocs/` for training POCs (optional but recommended)

### 📚 Training / Learning (Week 1)
- Revised core OOP principles:
    - Encapsulation, inheritance, polymorphism, abstraction
- Started JDBC training:
    - Connections, PreparedStatement, ResultSet
- Introduction to multithreading concepts:
    - Thread vs Runnable
    - Basic understanding of thread pools

### 🧩 Challenges Faced & Fixes
- Issue: <Example: DB connection error / port conflict / dependency download issue>
    - Root cause: <brief>
    - Fix applied: <brief steps>

### 📌 Week 1 Deliverables
- Spring Boot project created and runnable
- Database installed and accessible
- Initial repo structure prepared
- Week 1 documentation started

---

## 3. Week 2 Log - DB Schema + Entity Models + Documentation

### ✅ Tasks Completed
- Created initial database schema scripts:
    - `schema.sql` (tables for Suite, Case, Run, Result)
    - (Optional) `data.sql` for sample seed data
- Configured Spring Boot DB connection (without committing secrets)
    - Used `.example` configuration OR environment variables
- Defined Milestone 1 core entity models (with annotations):
    - `TestSuite`
    - `TestCase`
    - `TestRun`
    - `TestResult`
- Documented:
    - Requirements (`requirements.md`)
    - Architecture overview (`architecture.md`)
    - Data model (`data-model.md`)
- Added training proof (POCs) where applicable:
    - `pocs/jdbc-demo/` (basic connect + query)
    - `pocs/threading-demo/` (basic thread pool / parallel concept)
    - Optional: tiny `selenium-demo/` and `restassured-demo/` practice

### 📚 Training / Learning (Week 2)
- JDBC practice:
    - Simple insert/select queries
    - Understanding of DB schema mapping to entities
- Selenium / REST-Assured introduction:
    - Selenium: basic browser open + element interaction (concept-level)
    - REST-Assured: basic GET request + status assertion (concept-level)
- Multithreading:
    - Thread pool concept
    - Why parallel execution is important for regression test efficiency

### 🧩 Challenges Faced & Fixes
- Issue: <Example: JPA mapping confusion / schema mismatch / build errors>
    - Root cause: <brief>
    - Fix applied: <brief steps>

### 📌 Week 2 Deliverables
- DB schema scripts completed
- Core entity models completed with annotations
- Documentation package completed for Milestone 1
- POCs added (or training summary documented)

---

## 4. Evidence Checklist (Milestone 1)

- [ ] JDK installed & verified
- [ ] IDE configured and project runs
- [ ] Database installed and connected
- [ ] Spring Boot initialized (Maven)
- [ ] Training completed (OOP, JDBC, multithreading, Selenium/REST-Assured basics)
- [ ] Entity models defined with annotations
- [ ] DB schema scripts available
- [ ] Docs added: requirements, architecture, data model, weekly log

---

## 5. Key Learnings (Milestone 1)

- How to set up a professional Java + Spring Boot project structure
- How DB schema design supports future reporting and analytics
- Why entity modeling is critical before writing execution logic
- Basic understanding of JDBC and how it will store results
- Conceptual understanding of parallel execution benefits for faster test runs

---

## 6. Next Steps (Milestone 2 Preview Only)

> This section is only a preview; implementation will begin in Milestone 2.

- Start integrating Selenium and REST-Assured into the test execution engine
- Build initial Spring Boot APIs for test management
- Connect DB repositories for storing and retrieving test data