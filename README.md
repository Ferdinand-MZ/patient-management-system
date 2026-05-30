# Patient Management System

A microservices-based patient management backend built with **Spring Boot**, featuring JWT authentication, gRPC inter-service communication, and Kafka event streaming. also deployed on AWS (LocalStack for local dev) using ECS Fargate.

---

## System Architecture
<p align="center">
<img width="700" alt="patient-management-system" src="https://github.com/user-attachments/assets/3538bbac-a0ee-46bd-856f-0dbd88b50bd8" />
</p>

### Communication patterns

| Pattern | Used between | Protocol |
|---|---|---|
| REST / HTTP | Client → API Gateway → services | HTTP/JSON |
| JWT validation | API Gateway → Auth Service | HTTP GET `/validate` |
| Synchronous RPC | Patient Service → Billing Service | gRPC + Protobuf |
| Async event streaming | Patient Service → Analytics Service | Kafka + Protobuf |

---

## Services

### API Gateway · `:4004`
Entry point for all client traffic. Built on Spring Cloud Gateway. Routes requests to downstream services and enforces JWT authentication on protected routes via a custom `JwtValidationGatewayFilterFactory` that calls Auth Service's `/validate` endpoint before forwarding.

| Route | Destination | Auth required |
|---|---|---|
| `POST /auth/login` | Auth Service | No |
| `GET /auth/validate` | Auth Service | No |
| `/api/patients/**` | Patient Service | Yes — JWT |
| `/api-docs/patients` | Patient Service `/v3/api-docs` | No |
| `/api-docs/auth` | Auth Service `/v3/api-docs` | No |

---

### Auth Service · `:4005`
Handles user authentication and JWT lifecycle.

- `POST /login` — validates credentials against PostgreSQL, returns a signed JWT
- `GET /validate` — verifies a JWT token; called by the API Gateway filter on every protected request
- Uses Spring Security + JJWT for token signing and validation
- Has its own dedicated PostgreSQL database

---

### Patient Service · `:4000`
Core business service. Manages patient records via a REST API.

**Endpoints** (all require `Authorization: Bearer <token>` via API Gateway):

| Method | Path | Description |
|---|---|---|
| `GET` | `/patients` | List all patients |
| `POST` | `/patients` | Create a new patient |
| `PUT` | `/patients/{id}` | Update a patient |
| `DELETE` | `/patients/{id}` | Delete a patient |

**On patient creation**, the service does two things concurrently:
1. Calls **Billing Service** via gRPC (`CreateBillingAccount`) — synchronous, blocking
2. Publishes a `PATIENT_CREATED` Protobuf event to Kafka topic `"patient"` — asynchronous

Has its own dedicated PostgreSQL database. OpenAPI docs available at `/v3/api-docs`.

---

### Billing Service · `:4001` (HTTP) · `:9001` (gRPC)
Exposes a gRPC server that handles billing account creation. Receives `BillingRequest` (patientId, name, email) from Patient Service and returns a `BillingResponse` (accountId, status).

Proto definition:
```protobuf
service BillingService {
  rpc CreateBillingAccount (BillingRequest) returns (BillingResponse);
}
```

No database — stateless processing only.

---

### Analytics Service · `:4002`
Kafka consumer that listens on the `"patient"` topic (group: `analytics-service`). Deserializes Protobuf `PatientEvent` messages and processes patient lifecycle events (currently logs; designed to be extended with analytics logic).

Proto definition:
```protobuf
message PatientEvent {
  string patientId = 1;
  string name      = 2;
  string email     = 3;
  string event_type = 4;  // e.g. "PATIENT_CREATED"
}
```

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| API Gateway | Spring Cloud Gateway |
| Auth | Spring Security + JJWT |
| ORM | Spring Data JPA + Hibernate |
| Database | PostgreSQL (RDS) |
| RPC | gRPC + Protobuf |
| Messaging | Apache Kafka (AWS MSK) |
| Serialization | Protocol Buffers (proto3) |
| API Docs | SpringDoc OpenAPI (Swagger) |
| Infrastructure | AWS CDK (Java) |
| Local dev infra | LocalStack |
| Containerization | Docker + ECS Fargate |
| Testing | JUnit 5 + REST Assured |

---

## Project Structure

```
patient-management/
├── api-gateway/          # Spring Cloud Gateway + JWT filter
├── auth-service/         # Login, JWT issue & validate
├── patient-service/      # Patient CRUD, gRPC client, Kafka producer
├── billing-service/      # gRPC server for billing accounts
├── analytics-service/    # Kafka consumer for patient events
├── infrastructure/       # AWS CDK stack (LocalStack)
├── integration-tests/    # End-to-end REST Assured tests
├── api-requests/         # HTTP request samples (auth & patient)
└── grpc-requests/        # gRPC request samples (billing)
```

---

## Getting Started

### Prerequisites

- Java 17+
- Docker & Docker Compose
- Maven
- LocalStack CLI (for local AWS infra)
- AWS CLI

### Run locally

**1. Start infrastructure (PostgreSQL + Kafka via LocalStack)**

```bash
cd infrastructure
./localstack-deploy.sh
```

This synthesizes the CDK stack and deploys it to LocalStack, spinning up:
- 2× PostgreSQL RDS instances (auth-service-db, patient-service-db)
- AWS MSK Kafka cluster
- ECS Fargate services for all microservices

**2. Build all services**

```bash
# From each service directory
./mvnw clean package -DskipTests

# Or build all at once from root
for service in auth-service patient-service billing-service analytics-service api-gateway; do
  cd $service && ./mvnw clean package -DskipTests && cd ..
done
```

**3. Run services (local dev without Docker)**

Start each service in order:

```bash
# Auth Service
cd auth-service && ./mvnw spring-boot:run

# Billing Service
cd billing-service && ./mvnw spring-boot:run

# Patient Service
cd patient-service && ./mvnw spring-boot:run

# Analytics Service
cd analytics-service && ./mvnw spring-boot:run

# API Gateway (last)
cd api-gateway && ./mvnw spring-boot:run
```

---

## API Usage

### 1. Login and get a JWT

```http
POST http://localhost:4004/auth/login
Content-Type: application/json

{
  "email": "testuser@test.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 2. Create a patient

```http
POST http://localhost:4004/api/patients
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "address": "123 Main St",
  "dateOfBirth": "1990-01-15",
  "registeredDate": "2024-01-01"
}
```

### 3. Get all patients

```http
GET http://localhost:4004/api/patients
Authorization: Bearer <token>
```

### 4. Update a patient

```http
PUT http://localhost:4004/api/patients/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "address": "456 Oak Ave",
  "dateOfBirth": "1992-05-20"
}
```

### 5. Delete a patient

```http
DELETE http://localhost:4004/api/patients/{id}
Authorization: Bearer <token>
```

---

## Running Tests

```bash
# Unit tests (per service)
cd patient-service && ./mvnw test

# Integration tests (requires all services running on :4004)
cd integration-tests && ./mvnw test
```

The integration tests use REST Assured to:
1. Log in via the API Gateway and obtain a JWT
2. Call the `/api/patients` endpoint with the token and assert a `200` response

---

## API Documentation

Swagger UI is available via the API Gateway when services are running:

- Patient Service docs: `http://localhost:4004/api-docs/patients`
- Auth Service docs: `http://localhost:4004/api-docs/auth`

---

## Infrastructure (AWS CDK)

The `infrastructure/` module is an AWS CDK app written in Java that provisions:

| Resource | Detail |
|---|---|
| VPC | 2 availability zones |
| PostgreSQL RDS | `db.t2.micro`, 20GB — one per stateful service |
| AWS MSK | Kafka 2.8.0, 1 broker, `kafka.m5.xlarge` |
| ECS Cluster | Fargate, 256 CPU / 512MB per task |
| Application Load Balancer | Fronts the API Gateway only |
| CloudWatch Logs | 1-day retention per service |

All services run without a public IP because only the API Gateway is exposed via the ALB.

**Startup dependency order:**
```
PostgreSQL RDS (health check) → Auth Service
PostgreSQL RDS (health check) → Patient Service
MSK Kafka cluster             → Analytics Service
Billing Service               → Patient Service (gRPC dependency)
MSK Kafka cluster             → Patient Service (event producer)
```
