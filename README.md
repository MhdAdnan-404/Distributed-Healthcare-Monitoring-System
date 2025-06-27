## Project

<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/devicon.min.css" />

<br />

<div align="center">
  <h2 align="center">Distributed Healthcare Management System 🩺</h2>

  <p align="center">
    A system for managing patient care, appointments, prescriptions, vital signs, and medical alerts using a microservice architecture based on Domain-Driven Design (DDD).
  </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About the Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#the-architecture">The Architecture</a>
      <ul>
        <li><a href="#microservices--ddd">Microservices & DDD</a></li>
        <li><a href="#asynchronous-communication">Asynchronous Communication</a></li>
        <li><a href="#gateway--discovery">Gateway & Discovery</a></li>
        <li><a href="#domain-driven-design">Domain-Driven Design</a></li>
      </ul>
    </li>
    <li>
      <a href="#features">Features</a>
      <ul>
        <li><a href="#vital-sign-monitoring">Vital Sign Monitoring</a></li>
        <li><a href="#appointment-management">Appointment Management</a></li>
        <li><a href="#prescription-management">Prescription Management</a></li>
        <li><a href="#alert-notification-system">Alert Notification System</a></li>
        <li><a href="#security">Security</a></li>
        <li><a href="#caching--optimization">Caching & Optimization</a></li>
        <li><a href="#testing">Testing</a></li>
      </ul>
    </li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

---

## About the Project

<h4>
  <p>
    This system was designed to support distributed, scalable healthcare services for clinics or hospitals. At its core, it leverages Spring Boot microservices, each built around a clearly defined domain following Domain-Driven Design (DDD) principles. 
  </p>
</h4>

<p>
  Services communicate asynchronously using Kafka for streaming events such as abnormal vital sign detection, while synchronous communication is routed through a Spring Cloud Gateway using Eureka Service Discovery. The backend is decoupled for flexibility and performance, and data is persisted using PostgreSQL for structured records and MongoDB for dynamic, nested documents such as patient vitals.
</p>

<p>
  Redis serves as a high-performance cache for frequently accessed mappings, such as patient-device associations and alert limits.
</p>

<p align="right">(<a href="#project">back to top</a>)</p>

---

## Built With

<p align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original-wordmark.svg" height="60" style="margin: 0 10px;" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original-wordmark.svg" height="60" style="margin: 0 10px;" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postgresql/postgresql-original-wordmark.svg" height="60" style="margin: 0 10px;" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/mongodb/mongodb-original-wordmark.svg" height="60" style="margin: 0 10px;" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/docker/docker-original-wordmark.svg" height="60" style="margin: 0 10px;" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/apachekafka/apachekafka-original-wordmark.svg" height="60" style="margin: 0 10px;" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/redis/redis-original-wordmark.svg" height="60" style="margin: 0 10px;" />
</p>

---

## The Architecture

<p align="center">
  <img src="https://github.com/404dn/Distributed-Healthcare-Monitoring-System/blob/main/Photos/Design.png" alt="DDD Design" width="600"/>
</p>

---

<a id="microservices--ddd"></a>

### 🧩 Microservices & DDD

- **Spring Boot** used to structure services around core healthcare domains.
- **Bounded Contexts** modeled for:
  - Patient Management
  - Appointment Scheduling
  - Prescription Issuance
  - Vital Sign Monitoring
  - Notification Service
  - Call Management
  - Dynamic Timetable Management
- **CQRS** separates read/write responsibilities for scalability.

<a id="asynchronous-communication"></a>

### 🛰️ Asynchronous Communication

- **Apache Kafka** connects producers and consumers:
  - VitalSignConsumer listens for out-of-bound measurements.
  - NotificationService sends alerts to doctors and caretakers.
  - Kafka DTOs standardize message structure across services.

<a id="gateway--discovery"></a>

### 🌐 Gateway & Discovery

- **Spring Cloud Gateway** exposes all APIs under a centralized entry point.
- **Eureka Discovery** auto-registers services and supports load balancing.

<a id="domain-driven-design"></a>

### 🧠 Domain-Driven Design

<p align="center">
  <img src="https://github.com/yourusername/healthcare-system/blob/main/diagrams/ddd-contexts.png" alt="DDD Context Map" width="600"/>
</p>

<p align="right">(<a href="#project">back to top</a>)</p>

---

## Features

<a id="vital-sign-monitoring"></a>

### 🩺 Vital Sign Monitoring

- Real-time ingestion of heart rate, blood pressure, temperature, etc.
- Evaluation against dynamic device-patient limits.
- Abnormalities trigger Kafka alerts.

---

<a id="appointment-management"></a>

### 📅 Appointment Management

- Patients and doctors can create, cancel, or reschedule appointments.
- Each appointment is linked to a doctor, patient, and (optionally) prescription.

---

<a id="prescription-management"></a>

### 💊 Prescription Management

- Doctors can create prescriptions during appointments.
- Prescriptions include a list of medications and issuance date.
- Stored securely and sent as Kafka messages for further processing.

---

<a id="alert-notification-system"></a>

### 🚨 Alert Notification System

- Kafka-driven notifications for vital sign breaches.
- Custom notification templates for email/SMS.
- Configurable limits for each device and patient.

---

<a id="security"></a>

### 🔐 Security

- JWT-based authentication using Spring Security.
- Role-Based Access Control (RBAC) for Admins, Doctors, and Patients.
- Secured endpoints for service-to-service and client interactions.

---

<a id="caching--optimization"></a>

### ⚙️ Caching & Optimization

- Redis cache for device-vital mappings and query responses.
- TTL policies reduce load on MongoDB/PostgreSQL.
- CQRS enhances scalability and performance in high-read environments.

---

<a id="testing"></a>

### 🧪 Testing

- Unit and structural tests using **JUnit 5**.
- REST endpoint tests using **MockMvc**.
- Validation tests for DTOs and exception handling.

---

## License

Distributed under the MIT License. See `LICENSE.md` for more information.

<p align="right">(<a href="#project">back to top</a>)</p>
