# API Gateway - InvestBuddy AI

The **API Gateway** is a critical component of the **InvestBuddy AI** microservices architecture. It acts as a single entry point, managing authentication, routing, and enforcing security policies.

---

## 📜 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)

---

## ✨ Features

- **Dynamic Routing**: Routes requests to the appropriate microservices.
- **Authentication and Authorization**: Secures requests using OAuth2/JWT.
- **Error Handling**: Centralized and customized error responses.
- **Observability**: Integration with tools like **Zipkin** for monitoring.
- **Throttling and Rate Limiting**: Controls traffic to prevent abuse.

---

## 🏗️ Architecture

The API Gateway uses a **reverse proxy** architecture, leveraging the following tools:
- **Spring Cloud Gateway**: Manages requests and routing.
- **OAuth2**: For secure authentication using **Keycloak**
- **Eureka Client**: Discovers microservices via the Discovery Server.

---

## ✅ Prerequisites

Before starting, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.8** or higher
- A running Discovery Server (Eureka)
- A generated JWT authentication key

---

## 🛠️ Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/InvestBuddy/api-gateway.git
   cd api-gateway
   
2. Install dependencies using Maven:

    ```bash
   mvn clean install
   
3. Start the service:

    ```bash
   mvn spring-boot:run
   
## ▶️ Usage

1. **Access Endpoints**: Once the API Gateway is running, use a client like Postman to test the routes:

    Example for retrieving users from the User Service:

    ```bash
   GET http://localhost:8080/api/v1/users

2. **Authentication**:

    Add a JWT token in the Authorization header for secured endpoints:

    ```bash
    Authorization: Bearer <your-token>