# Materials Management System

This is a backend project developed in **Java** using **Spring Boot**. The main purpose of the application is the management of materials, their statuses, and their locations (cities and departments).

The project is designed strictly following the principles of **Hexagonal Architecture (Ports and Adapters)** to ensure a high level of maintainability, testability, and separation of concerns.

## 🛠 Technologies Used

- **Language:** Java
- **Framework:** Spring Boot (3.5.15)
  - Spring Web
  - Spring Data JPA
- **Database:** PostgreSQL 18
- **Build Tool & Dependencies:** Gradle (Kotlin DSL)
- **API Documentation:** Springdoc OpenAPI (Swagger)
- **Utilities:** Lombok
- **Infrastructure:** Docker & Docker Compose

## 🏗 Architecture

The project is divided into three main submodules according to the Hexagonal Architecture:

1. **`domain`**: Contains the pure business logic, domain models (entities like `Material`, `City`, `Department`), and the interfaces (ports) that define how the domain communicates with the outside world. It has no dependencies on external frameworks.
2. **`application`**: Contains the application use cases (e.g., Register a new material). It acts as an orchestrator, using the ports defined in the domain layer.
3. **`infrastructure`**: Contains the implementation details (adapters). Here you will find:
   - REST Controllers (Driving/Primary adapters).
   - JPA Database Repositories (Driven/Secondary adapters).
   - Mappers to convert DTOs to Domain Entities and vice versa.
   - Global exception handling (`@RestControllerAdvice`).

## 📋 Prerequisites

To run this project locally, you need to have installed:

- [Java JDK 17 or higher](https://adoptium.net/) (Ensure compatibility with Spring Boot 3.x)
- [Docker](https://www.docker.com/) and Docker Compose
- Optional: A compatible IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 How to Run the Project

1. **Start the database**
   In the root of the project, run the following command to start the PostgreSQL container:
   ```bash
   docker-compose up -d
   ```
   *Note: Make sure to configure the necessary environment variables (e.g., `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `DB_EXTERNAL_PORT`, `DB_INTERNAL_PORT`) in a `.env` file or in your environment before running Docker.*

2. **Build and run the application**
   You can run the application using the included Gradle wrapper:
   ```bash
   ./gradlew :infrastructure:bootRun
   ```

3. **Access the API documentation**
   Once the application is running, you can access the Swagger UI to view and test the endpoints:
   - URL: `http://localhost:8080/swagger-ui.html` (or the configured port).