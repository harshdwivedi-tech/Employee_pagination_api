# Employee Management & Pagination API

A production-oriented **Spring Boot REST API** for managing employee records with complete CRUD operations, server-side pagination, dynamic sorting, and keyword-based search.

The project demonstrates how to design a layered REST API using **Spring Boot, Spring Data JPA, PostgreSQL, and Hibernate**, while efficiently handling large employee datasets through database-level pagination and sorting.

## Features

* Employee CRUD operations
* Server-side pagination
* Dynamic sorting
* Keyword-based employee search
* PostgreSQL database integration
* Spring Data JPA and Hibernate
* Layered architecture
* RESTful API design
* Swagger/OpenAPI documentation
* Lombok for reducing boilerplate code
* Environment-variable-based database configuration
* Docker support

---

## Tech Stack

| Technology        | Purpose                         |
| ----------------- | ------------------------------- |
| Java 21           | Programming language            |
| Spring Boot 4.0.7 | Backend framework               |
| Spring Web MVC    | REST API development            |
| Spring Data JPA   | Data access and persistence     |
| Hibernate         | ORM                             |
| PostgreSQL        | Relational database             |
| Lombok            | Boilerplate code reduction      |
| Springdoc OpenAPI | Swagger/OpenAPI documentation   |
| Maven             | Build and dependency management |
| Docker            | Containerization                |

---

## Architecture

The application follows a layered Spring Boot architecture:

```text
                    Client
                      │
                      ▼
              REST Controller
                      │
                      ▼
                Service Layer
                      │
                      ▼
              Repository Layer
                      │
                      ▼
                Spring Data JPA
                      │
                      ▼
                  PostgreSQL
```

### Layer Responsibilities

**Controller**

Handles HTTP requests, request parameters, and API responses.

**Service**

Contains the application's business logic, including employee operations, pagination, sorting, and searching.

**Repository**

Handles database interaction through Spring Data JPA.

**Entity**

Represents employee data stored in the PostgreSQL database.

---

## Core Functionality

### 1. Employee Management

The API supports the standard CRUD lifecycle:

```text
Create
  ↓
Read
  ↓
Update
  ↓
Delete
```

This makes the API suitable as a backend foundation for an employee management application.

---

### 2. Server-Side Pagination

Instead of loading every employee record into memory, the API retrieves only the required page of data from the database.

Conceptually:

```text
Large Employee Dataset
        │
        ▼
 ┌───────────────┐
 │ Page 0        │
 ├───────────────┤
 │ Page 1        │
 ├───────────────┤
 │ Page 2        │
 ├───────────────┤
 │     ...       │
 └───────────────┘
```

This approach reduces unnecessary data transfer and improves API scalability when working with larger datasets.

---

### 3. Dynamic Sorting

Employee results can be ordered dynamically based on a selected field and sort direction.

Example:

```text
sortBy=name
sortDir=asc
```

or:

```text
sortBy=id
sortDir=desc
```

This allows clients to control how employee records are displayed without changing the backend implementation.

---

### 4. Keyword Search

The API supports searching employee records using a keyword.

For example:

```text
keyword=harsh
```

The service layer processes the search request and retrieves matching employee records from the database.

---

## API

The API is designed around employee resources.

### Create Employee

```http
POST /api/employees
Content-Type: application/json
```

Example request:

```json
{
  "name": "Harsh Dwivedi",
  "email": "harsh@example.com"
}
```

---

### Get Employees

```http
GET /api/employees
```

Supports pagination, sorting, and search parameters.

Example:

```http
GET /api/employees?page=0&size=10
```

With sorting:

```http
GET /api/employees?page=0&size=10&sortBy=name&sortDir=asc
```

With search:

```http
GET /api/employees?page=0&size=10&keyword=harsh
```

> Adjust the parameter names above if you change the controller contract; the README should always match the exact endpoint implementation.

---

### Get Employee by ID

```http
GET /api/employees/{id}
```

Example:

```http
GET /api/employees/1
```

---

### Update Employee

```http
PUT /api/employees/{id}
Content-Type: application/json
```

Example:

```http
PUT /api/employees/1
```

---

### Delete Employee

```http
DELETE /api/employees/{id}
```

Example:

```http
DELETE /api/employees/1
```

---

## Pagination Concept

A typical paginated response can be represented as:

```json
{
  "content": [
    {
      "id": 1,
      "name": "Employee Name",
      "email": "employee@example.com"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 100,
  "totalPages": 10
}
```

Pagination allows clients to retrieve employee records in manageable chunks rather than requesting the entire dataset at once.

---

## Search + Pagination + Sorting

The API can combine these operations.

Example:

```http
GET /api/employees?page=0&size=10&keyword=dev&sortBy=name&sortDir=asc
```

Conceptually:

```text
                   Employee Database
                          │
                          ▼
                     Keyword Search
                          │
                          ▼
                       Sorting
                          │
                          ▼
                      Pagination
                          │
                          ▼
                    JSON Response
```

This approach keeps filtering and pagination close to the data layer rather than transferring the entire dataset to the application.

---

## Project Structure

```text
Employee_pagination_api/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── pagination/
│   │   │
│   │   │               ├── controller/
│   │   │               │   └── EmployeeController.java
│   │   │               │
│   │   │               ├── entity/
│   │   │               │   └── Employee.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   └── EmployeeRepository.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   └── EmployeeService.java
│   │   │               │
│   │   │               └── PaginationApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/example/pagination/
│
├── .mvn/
│   └── wrapper/
│
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
└── .gitignore
```

The repository currently follows this controller → service → repository → entity separation.

---

## Prerequisites

Before running the application, make sure you have:

* Java 21
* Maven
* PostgreSQL
* Git
* Docker (optional)

---

## Database Configuration

The current application configuration expects database values to be supplied through environment variables:

```properties
spring.application.name=pagination

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PWD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

The repository therefore does not require database credentials to be hardcoded into `application.properties`.

### Environment Variables

Set:

```text
DB_URL
DB_USERNAME
DB_PWD
```

Example:

```text
DB_URL=jdbc:postgresql://localhost:5432/employee_db
DB_USERNAME=postgres
DB_PWD=your_password
```

Create the database before starting the application:

```sql
CREATE DATABASE employee_db;
```

---

## Running Locally

### 1. Clone the repository

```bash
git clone https://github.com/harshdwivedi-tech/Employee_pagination_api.git
```

### 2. Navigate into the project

```bash
cd Employee_pagination_api
```

### 3. Configure PostgreSQL

Create the database and configure:

```text
DB_URL
DB_USERNAME
DB_PWD
```

### 4. Build the application

Linux/macOS:

```bash
./mvnw clean install
```

Windows:

```bash
mvnw.cmd clean install
```

### 5. Run the application

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

---

## Running with Docker

The repository includes a `Dockerfile`, allowing the application to be containerized.

Build the image:

```bash
docker build -t employee-pagination-api .
```

Run the container:

```bash
docker run -p 8080:8080 \
  -e DB_URL="jdbc:postgresql://host.docker.internal:5432/employee_db" \
  -e DB_USERNAME="postgres" \
  -e DB_PWD="your_password" \
  employee-pagination-api
```

Make sure the PostgreSQL instance is accessible from the container.

---

## API Documentation

The project includes **Springdoc OpenAPI** support through:

```text
springdoc-openapi-starter-webmvc-ui
```

This can be used to expose interactive Swagger/OpenAPI documentation for the REST API.

After starting the application, check the configured Swagger UI endpoint, commonly:

```text
http://localhost:8080/swagger-ui/index.html
```

The exact URL can vary with configuration and Springdoc version.

---

## Testing

The API can be tested using:

* Swagger UI
* Postman
* cURL
* IntelliJ IDEA HTTP Client

A typical testing workflow:

```text
1. Start PostgreSQL
        ↓
2. Configure environment variables
        ↓
3. Start Spring Boot application
        ↓
4. Open Swagger/Postman
        ↓
5. Create employees
        ↓
6. Retrieve employees
        ↓
7. Test pagination
        ↓
8. Test sorting
        ↓
9. Test keyword search
        ↓
10. Test update/delete operations
```

---

## What This Project Demonstrates

This project demonstrates practical backend development concepts including:

* REST API development
* Spring Boot application structure
* CRUD operations
* Spring Data JPA
* Hibernate ORM
* PostgreSQL integration
* Repository pattern
* Service layer design
* Controller layer design
* Server-side pagination
* Dynamic sorting
* Keyword-based searching
* Database-backed filtering
* OpenAPI/Swagger documentation
* Environment-based configuration
* Docker containerization

---

## Future Improvements

Possible enhancements include:

* Request/response DTOs
* Jakarta Bean Validation
* Global exception handling
* Standardized API error responses
* Advanced multi-field search
* Sorting by multiple fields
* Pagination metadata DTO
* Unit and integration test coverage
* Database migrations using Flyway or Liquibase
* Docker Compose for application + PostgreSQL
* CI/CD with GitHub Actions
* Production profile configuration
* API versioning
* Logging and monitoring
* Health checks using Spring Boot Actuator

---

## Author

**Harsh Dwivedi**

GitHub: [@harshdwivedi-tech](https://github.com/harshdwivedi-tech)

---

## Support

If this project helped you understand Spring Boot, JPA, pagination, sorting, or REST API development, consider giving the repository a star.
