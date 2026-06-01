# Checking Account Operation 🏦

[![Spring Boot](https://shields.io)](https://spring.io)
[![Java](https://shields.io)](https://oracle.com)

_Read this in other languages: [Português](README.pt-br.md)_

A Spring Boot microservice designed to manage checking account operations, focusing on executing and processing electronic cash withdrawals and data persistence.

## 🚀 Technologies Used

*   **Java 17+** - Core language.
*   **Spring Boot 3.x** - Framework for building the microservice.
*   **Spring Data JPA** - Abstraction layer for database communication.
*   **H2 Database** - In-memory database for fast development and testing.
*   **Lombok** - Boilerplate code reduction (getters, setters, constructors).
*   **Springdoc OpenAPI (Swagger)** - Interactive API documentation.

## 📋 API Endpoints

### Withdrawals

#### Execute a Withdrawal and return cash notes
*   **URL:** `/accounts/withdrawals/{accountId}`
*   **Method:** `POST`
*   **Request Body (JSON):**
    ```json
    {
      "amount": 150.00
    }
    ```
*   **Responses:**
    *   `201 Created`: Withdrawal successful. Returns transaction details and banknote distribution.
    *   `400 Bad Request`: Invalid data (e.g., negative amount).
    *   `422 Unprocessable Entity`: Insufficient funds to complete the operation.


#### Search for withdrawals transactions
*   **URL:** `/accounts/withdrawals/{accountId}`
*   **Method:** `GET`
*   **Responses:**
    *   `201 Created`: Withdrawal successful. Returns transaction details and banknote distribution.
    *   `400 Bad Request`: Invalid data (e.g., negative amount).
    *   `422 Unprocessable Entity`: Insufficient funds to complete the operation.


---

## 🔧 How to Run the Project

### Prerequisites
*   Java 17 or higher installed.
*   Maven 3.8+ installed (or use the `./mvnw` wrapper).

### Step-by-Step

1. **Clone the repository:**
   ```bash
   git clone https://github.com
   cd operacao-conta-corrente
   ```

2. **Build the project:**
   ```bash
   ./mvnw clean package
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start by default on port `8080`.

---

## 🔍 Useful Links (While App is Running)

*   **Swagger UI Documentation:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
*   **H2 Database Console:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
