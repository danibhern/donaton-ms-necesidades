# 🤝 donaton-ms-necesidades

Microservicio de **gestión de necesidades en terreno** perteneciente a la plataforma **Donaton** — sistema inteligente de coordinación de ayuda humanitaria.

> Proyecto semestral · DSY1106 Desarrollo Fullstack III · Evaluación Parcial 3

---

## 📋 Descripción

`ms-necesidades` permite a usuarios externos reportar necesidades específicas de recursos (tipo, cantidad y ubicación geográfica), facilitando la coordinación de ayuda humanitaria en situaciones de emergencia o crisis social.

---

## 🏗️ Arquitectura del sistema

```text
Frontend  (5173)
     │
     ▼
BFF Gateway (8080)  ←  valida JWT + Circuit Breaker
     │
     ├──► ms-usuarios   (8081)  →  dbusuarios   (MySQL 3306)
     ├──► ms-donaciones (8082)  →  dbdonaciones (MySQL 3306)
     └──► ms-necesidades(8083)  →  dbnecesidades(MySQL 3306)
```

---

## ⚙️ Stack tecnológico

| Componente        | Tecnología                     |
|-------------------|--------------------------------|
| Lenguaje          | Java 21                        |
| Framework         | Spring Boot 3.5.1              |
| Persistencia      | Spring Data JPA + MySQL        |
| Validaciones      | Jakarta Validation             |
| Boilerplate       | Lombok                         |
| Build             | Maven                          |
| Pruebas unitarias | JUnit 5 + Mockito              |
| Documentación API | SpringDoc OpenAPI (Swagger UI) |
| Monitoreo         | Spring Actuator                |

---

## 📁 Estructura del proyecto

```text
donaton-ms-necesidades/
├── src/
│   ├── main/
│   │   ├── java/com/donaton/necesidades/
│   │   │   ├── MsNecesidadesApplication.java
│   │   │   ├── model/
│   │   │   │   └── Necesidad.java
│   │   │   ├── enums/
│   │   │   │   ├── TipoRecurso.java
│   │   │   │   ├── EstadoNecesidad.java
│   │   │   │   └── NivelUrgencia.java
│   │   │   ├── repository/
│   │   │   │   └── NecesidadRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── NecesidadRequestDTO.java
│   │   │   │   └── NecesidadResponseDTO.java
│   │   │   ├── service/
│   │   │   │   ├── NecesidadService.java
│   │   │   │   └── NecesidadServiceImpl.java
│   │   │   ├── controller/
│   │   │   │   └── NecesidadController.java
│   │   │   └── exception/
│   │   │       ├── NecesidadNotFoundException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/donaton/necesidades/
│           ├── model/
│           │   └── NecesidadTest.java
│           └── service/
│               └── NecesidadServiceImplTest.java
└── pom.xml
```

---

## 🔧 Configuración

### `application.properties`

```properties
server.port=8083
spring.application.name=ms-necesidades

spring.datasource.url=jdbc:mysql://localhost:3306/dbnecesidades?createDatabaseIfNotExists=true
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Puerto MySQL:** `3306` · **Puerto del microservicio:** `8083`

---

## 🚀 Cómo ejecutar

### Prerrequisitos

- Java 21
- MySQL corriendo en el puerto `3306`
- Maven 3.9+

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/danibhern/donaton-ms-necesidades.git
cd donaton-ms-necesidades

# 2. Posicionarse en develop
git checkout develop
git pull origin develop

# 3. Ejecutar
./mvnw spring-boot:run
```

El microservicio queda disponible en: `http://localhost:8083`


## 🌿 GitFlow

| Rama                             | Propósito                         | Responsable |
|----------------------------------|-----------------------------------|-------------|
| `main`                           | Código final de producción        | —           |
| `develop`                        | Integración de features           | —           |
| `feature/ms-necesidades-base`    | Model, Enums, Repository, DTOs    | Daniela     |
| `feature/ms-necesidades-service` | Service, Controller, Tests        | Yesenia     |

---

## 🧩 Patrones de diseño aplicados

| Patrón               | Clase / Componente                   | Responsable      |
|----------------------|--------------------------------------|------------------|
| Repository Pattern   | `NecesidadRepository`                | Daniela          |
| Factory Method       | `NecesidadFactory`                   | Yesenia          |
| Database per Service | `dbnecesidades` MySQL 3306           | Daniela / Yesenia|
| Circuit Breaker      | `NecesidadFacade` en BFF Gateway     | Alexis           |

---

## 👥 Equipo

| Integrante | Responsabilidad                         |
|------------|-----------------------------------------|
| Daniela    | Model, Enums, Repository, DTOs, Tests   |
| Yesenia    | Service, Controller, Exception Handler  |
| Alexis     | BFF Gateway + Circuit Breaker           |
| Matías     | Frontend React + Vite                   |
