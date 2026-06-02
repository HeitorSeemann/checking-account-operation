# Checking Account Operation 🏦

A hybrid Spring Boot service developed to process checking account operations, specifically cash withdrawals. The application architecture supports both synchronous communication via a REST API and asynchronous event processing via Apache Kafka.

Every withdrawal execution triggers core business logic to validate balance and compute the optimized distribution of banknotes.

## 🚀 Technologies

- **Java 17**
- **Spring Boot 3.x**
- **Gradle**
- **Apache Kafka** (Asynchronous event processing)
- **H2 Database** (In-Memory for transaction history and state)
- **Spring Data JPA**

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

### 1. Run Infrastructure
Start the Kafka broker using Docker Compose:
```bash
docker-compose up -d
```

### 2. Build and Start the Application
Compile and execute using Gradle:
```bash
./gradlew clean build
./gradlew bootRun
```
The application will listen on HTTP port `8080` and subscribe to the `withdrawal-requests` Kafka topic simultaneously.
