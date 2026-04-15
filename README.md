# Quantity Measurement App
**Project — Unit conversion application supporting multiple physical quantity types with a clean N-Tier architecture.**

## Table of Contents
- [Overview](#overview)
- [Supported Units](#supported-units)
- [Folder Structure](#folder-structure)
- [Use Cases](#use-cases)
- [Important Information](#important-information)

---

## Overview
The **Quantity Measurement App (Quantix)** is designed to facilitate the conversion, comparison, and arithmetic operation of various physical quantities. It supports multiple measurement units and provides a structured, secure REST API suitable for full-stack integration.

This project focuses on object-oriented design principles, test-driven development (TDD), and a clean **N-Tier layered architecture** to ensure scalability and maintainability.

**Key Highlights:**
- Accurate unit conversion across multiple quantity families (Length, Weight, Temp, Volume).
- Type-safe implementation using `Double.compare()` and robust null checks.
- Full JWT and OAuth2 (Google/GitHub) security integration.
- Persisted operation history with Spring Data JPA.
- Interactive API documentation via Swagger/OpenAPI.

---

## Supported Units
| Category | Units Supported |
| :--- | :--- |
| **Length** | Meters, Kilometers, Miles, Yards, Feet, Inches |
| **Weight** | Grams, Kilograms, Pounds, Ounces |
| **Temperature** | Celsius, Fahrenheit, Kelvin |
| **Volume** | Litre, MilliLitre, Gallon |

---

## Folder Structure
```text
quantity-measurement-app/
├── src/
│   ├── main/
│   │   └── java/com/app/quantitymeasurement/
│   │       ├── config/         # Security, CORS, and MVC Configurations
│   │       ├── controller/     # REST Controllers for Auth and Measurements
│   │       ├── dto/            # Data Transfer Objects for Request/Response
│   │       ├── model/          # JPA Entities and Domain Models
│   │       ├── repository/     # Data Access Layer (Spring Data JPA)
│   │       ├── security/       # JWT Filters, OAuth2 Handlers, UserDetails
│   │       ├── service/        # Business Logic & Core Measurement Engines
│   │       └── unit/           # Physical Dimension Enums (Length, Weight, etc.)
│   └── test/
│       └── java/com/app/quantitymeasurement/
│           ├── controller/     # Integration tests for API endpoints
│           ├── service/        # Unit tests for business logic
│           └── unit/           # Core conversion logic validation
├── pom.xml
└── README.md
```

---

## Use Cases
The application is validated through 15 use cases, each covering a specific conversion scenario or design concern.

| Use Case | Title | Key Concepts |
| :--- | :--- | :--- |
| **UC-01** | Feet Measurement Equality | `Double.compare()`, null checking, type safety |
| **UC-02** | Feet to Inches Conversion | Unit conversion logic, conversion factors |
| **UC-03** | Inch Measurement | Symmetrical class design |
| **UC-04** | Feet & Inch Addition | Unit standardization, arithmetic operations |
| **UC-05** | Yard Measurement | Multi-level unit hierarchy |
| **UC-06** | Centimeter Measurement | Metric system introduction |
| **UC-07** | Centimeter to Meter | Metric hierarchy validation |
| **UC-08** | Imperial to Metric | Cross-family conversions |
| **UC-09** | Kilometer Distance | Extended metric system |
| **UC-10** | Mile Measurement | Imperial long-distance units |
| **UC-11** | Temperature Conversion | Non-linear transformations (offset arithmetic) |
| **UC-12** | Volume Measurement | Litres and Millilitres |
| **UC-13** | Weight Measurement | Grams and Kilograms |
| **UC-14** | Compound Operations | Multi-dimensional unit handling (Add/Sub/Div) |
| **UC-15** | N-Tier Architecture | Layered architecture, separation of concerns |

---

## Important Information
- **Security**: Ensure JWT secrets and OAuth2 credentials are configured in `application.properties` before running in production.
- **Precision**: Temperature conversions involve non-linear formulas. Always use `Double.compare()` for floating-point equality checks — avoid using `==`.
- **TDD**: All use cases are backed by JUnit 5 test suites. Run `mvn test` to execute the full suite before deployment.
- **API Access**: Access the interactive documentation at `/swagger-ui.html` once the server is running.

---

**Technology Stack:** Java · Spring Boot · Spring Security · JWT · OAuth2 · PostgreSQL · Hibernate · JUnit 5 · Maven · N-Tier Architecture
