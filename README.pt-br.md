# Operação Conta Corrente 🏦

Microserviço desenvolvido em Spring Boot projetado para gerenciar operações de conta corrente, com foco inicial na execução e processamento de saques eletrônicos e persistência de dados.

## 🚀 Tecnologias Utilizadas

*   **Java 17+** - Linguagem base do ecossistema.
*   **Spring Boot 3.x** - Framework para construção do microserviço.
*   **Spring Data JPA** - Abstração de persistência e comunicação com o banco.
*   **H2 Database** - Banco de dados em memória para desenvolvimento e testes rápidos.
*   **Lombok** - Redução de código boilerplate (getters, setters, construtores).
*   **Springdoc OpenAPI (Swagger)** - Documentação interativa dos endpoints.

## 🛠️ Arquitetura do Projeto

O projeto segue as melhores práticas da arquitetura em camadas do Spring:

```text
src/main/java/com/heitorseemann/operacaocontacorrente/
│
├── controller/     # Porta de entrada da API (Endpoints REST)
├── service/        # Regras de negócio (Lógica de saque e validações)
├── repository/     # Interface de comunicação com o Banco de Dados
├── model/          # Entidades de domínio (Conta, Saque, etc.)
└── exception/      # Tratamento global de erros da aplicação
```

## 📋 Endpoints da API

Abaixo estão as principais rotas configuradas no sistema:

### Saques

#### Efetuar um Saque
*   **URL:** `/contas/{contaId}/saques`
*   **Método:** `POST`
*   **Corpo da Requisição (JSON):**
    ```json
    {
      "valor": 150.00
    }
    ```
*   **Respostas:**
    *   `201 Created`: Saque realizado com sucesso. Retorna os detalhes da transação e a distribuição de cédulas.
    *   `400 Bad Request`: Dados inválidos (ex: valor negativo).
    *   `422 Unprocessable Entity`: Saldo insuficiente para completar a operação.

---

## 🔧 Como Executar o Projeto

### Pré-requisitos
*   Java 17 ou superior instalado.
*   Maven 3.8+ instalado (ou utilize o wrapper `./mvnw`).

### Passo a Passo

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com
   cd operacao-conta-corrente
   ```

2. **Compilar o projeto:**
   ```bash
   ./mvnw clean package
   ```

3. **Executar a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

A aplicação iniciará por padrão na porta `8080`.

---

## 🔍 Links Úteis com o App Rodando

*   **Documentação Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
*   **Console do Banco H2:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    *   *Nota: Verifique as credenciais `jdbc:url`, `username` e `password` no arquivo `application.properties`.*

---

## 🧪 Executando os Testes

Para rodar a suíte de testes unitários e de integração, execute:

```bash
./mvnw test
```

---

## ✒️ Autor

*   **Heitor Seemann** - *Desenvolvimento Inicial* - [HeitorSeemann](https://github.com)

---
Este projeto foi desenvolvido para fins de estudo e práticas de arquitetura de software com Spring Boot.
