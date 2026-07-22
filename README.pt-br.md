# Checking Account Operation 2026 🏦

Um serviço Spring Boot híbrido desenvolvido para processar operações de conta corrente, especificamente saques em dinheiro. A arquitetura da aplicação suporta tanto comunicação síncrona via API REST quanto processamento assíncrono de eventos via Apache Kafka.

Qualquer execução de saque aciona as regras centrais de negócio para validar saldo e calcular a distribuição otimizada de cédulas.

## 🚀 Tecnologias

- **Java 25**
- **Spring Boot 3.x**
- **Gradle**
- **Apache Kafka** (Processamento assíncrono de eventos)
- **H2 Database** (Em memória para histórico de transações)
- **Spring Data JPA**

## 🔄 Padrões de Comunicação da Arquitetura

A aplicação processa operações através de dois pontos de entrada principais:
1. **Interface Síncrona**: Clientes chamam controladores REST diretamente via HTTP, recebendo uma resposta confirmatória imediata e bloqueante.
2. **Interface Assíncrona**: Outros microsserviços ou produtores publicam eventos em um stream do Kafka. Um componente consumidor dedicado processa a mensagem de forma sequencial.

Ambos os fluxos encaminham as requisições para a mesma camada de serviço onde ocorrem as validações e os cálculos.

## 📋 1. Interface Síncrona (API REST)

### Realizar um Saque
* **URL:** `/accounts/withdrawals/{accountId}`
* **Método:** `POST`
* **Variável de Caminho:** `accountId` (Long)
* **DTO de Requisição (Corpo):**
  ```json
  {
    "amount": 130.00
  }
  ```
* **DTO de Resposta (201 Created):**
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

### Consultar Histórico de Saques
* **URL:** `/accounts/withdrawals/{accountId}`
* **Método:** `GET`
* **Variável de Caminho:** `accountId` (Long)
* **DTO de Resposta (200 OK):**
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

## 🔄 2. Interface Assíncrona (Apache Kafka)

### Consumidor de Mensagens de Entrada
O serviço atua como consumidor escutando eventos recebidos para disparar saques de forma assíncrona.

* **Tópico de Entrada:** `withdrawal-requests`
* **DTO da Mensagem (Payload):**
  ```json
  {
    "accountId": 1001,
    "amount": 280.00,
    "timestamp": "2026-06-02T16:31:00Z"
  }
  ```

## 🧮 Lógica de Otimização de Cédulas

A aplicação calcula a menor quantidade possível de notas necessárias para o saque utilizando cédulas de: `R$100`, `R$50`, `R$20` e `R$10`.

## 🔧 Como Executar

### 1. Subir Infraestrutura
Inicie o broker do Kafka utilizando o Docker Compose:
```bash
docker-compose up -d
```

### 2. Compilar e Iniciar a Aplicação
Compile e execute o projeto utilizando o Gradle:
```bash
./gradlew clean build
./gradlew bootRun
```
A aplicação responderá na porta HTTP `8080` e escutará mensagens no tópico do Kafka simultaneamente.
