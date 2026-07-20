# Checking Account Operation 🏦

A hybrid Spring Boot service developed to process checking account operations, specifically cash withdrawals. The application architecture supports both synchronous communication via a REST API and asynchronous event processing via Apache Kafka.

Every withdrawal execution triggers core business logic to validate balance and compute the optimized distribution of banknotes.

## 🚀 Technologies

- **Java 25**
- **Spring Boot**
- **Gradle**
- **Apache Kafka** (Asynchronous event processing via Docker KRaft mode)
- **H2 Database** (In-Memory for transaction history and state embedded within the process)
- **Spring Data JPA**
- **Docker & Docker Compose** (Containerization and infrastructure orchestration)
- **Sonarqube**

## 🔄 Architectural Communication Patterns

The application processes operations through two entrypoints:
1. **Synchronous Interface**: Clients invoke REST controllers directly over HTTP, receiving a blocking confirmation response.
2. **Asynchronous Interface**: Other microservices or event producers publish events to an inbound Kafka stream. A dedicated consumer component picks up and processes the message sequentially.

Both patterns route requests through the exact same service layer to execute validation rules and calculation logic.

## 📋 1. Synchronous Interface (REST API)

### Create a Withdrawal
* **URL:** `/accounts/withdrawals/{accountId}`
* **Method:** `POST`
* **Path Variable:** `accountId` (Long)
* **Request DTO (Body):**
  ```json
  {
    "amount": 130.00
  }
  ```
* **Response DTO (201 Created):**
  ```json
  {
    "id": 1,
    "accountId": 1001,
    "amount": 130.00,
    "timestamp": "2026-06-02T16:30:00Z",
    "banknotes": {
      "100": 1,
      "20": 1,
      "10": 1
    }
  }
  ```

### Get Withdrawal History
* **URL:** `/accounts/withdrawals/{accountId}`
* **Method:** `GET`
* **Path Variable:** `accountId` (Long)
* **Response DTO (200 OK):**
  ```json
  [
    {
      "id": 1,
      "accountId": 1001,
      "amount": 130.00,
      "timestamp": "2026-06-02T16:30:00Z",
      "banknotes": {
        "100": 1,
        "20": 1,
        "10": 1
      }
    }
  ]
  ```

## 🔄 2. Asynchronous Interface (Apache Kafka)

### Inbound Message Consumer
The service acts as a consumer listening to incoming events to trigger withdrawals asynchronously.

* **Inbound Topic:** `withdrawal-requests`
* **Message DTO (Payload):**
  ```json
  {
    "accountId": 1001,
    "amount": 280.00,
    "timestamp": "2026-06-02T16:31:00Z"
  }
  ```

## 🧮 Banknote Optimization Logic

The application calculates the minimum number of bills required for a withdrawal using the standard denominations: `$100`, `$50`, `$20`, and `$10`.

## 🔧 How to Run

You can choose to spin up the entire application stack using Docker (Recommended) or run a hybrid local environment.

### Option 1: Full Execution via Docker (Recommended)

This method packages the application into a lightweight container and orchestrates it alongside Apache Kafka inside an isolated network.

1. Compile and package the executable application binary (`.jar`) on your host machine:
   ```bash
   ./gradlew bootJar
   ```
2. Build the final runtime container image and spin up the complete stack in the background:
   ```bash
   docker compose up --build -d
   ```
   The Spring Boot REST API will be immediately reachable at `http://localhost:8080` and dynamically linked to the Kafka broker.

### Option 2: Hybrid Execution (Kafka in Docker + Local App)

Ideal for active local development, fast iterations, and quick debugging via an IDE.

1. Spin up **only** the Apache Kafka infrastructure container:
   ```bash
   docker compose up kafka -d
   ```
2. Compile and run the Spring Boot application natively on your host machine:
   ```bash
   ./gradlew bootRun
   ```

---

## 🧪 Running Tests

To achieve optimal build performance, instant test execution loops, and avoid cross-OS file lock contention (especially on Windows hosts sharing volumes with WSL), the recommended development workflow is to execute the test suite on the host machine using the Docker-backed Kafka broker.

1. Ensure the Kafka container environment is running:
   ```bash
   docker compose up kafka -d
   ```
2. Trigger the Gradle test execution runner task:
   ```bash
   ./gradlew test
   ```
3. A complete, interactive HTML test execution report will be generated. You can inspect the results by opening the following file directly in your browser:
   `build/reports/tests/test/index.html`

