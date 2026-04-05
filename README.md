# Smart School API

Spring Boot API for a smart school system with JWT authentication, role-based access, and PostgreSQL persistence.

## Features

- JWT login and role-based authorization
- Admin/Teacher/Student endpoints
- PostgreSQL with Hibernate JPA
- CORS support for web clients

## Tech Stack

- Java 21
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- Maven

## Quick Start (Local)

### 1) Prerequisites

- Java 21
- Maven (or use the included `mvnw`)
- PostgreSQL

### 2) Configure Environment Variables

Set the following environment variables before running the app:

- `DATABASE_URL` (JDBC URL)
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`

Example (PowerShell):

```powershell
$env:DATABASE_URL="jdbc:postgresql://localhost:5433/smart_school_db"
$env:DATABASE_USERNAME="postgres"
$env:DATABASE_PASSWORD="admin123"
```

### 3) Run the App

```powershell
D:\smart-school-api\mvnw.cmd spring-boot:run
```

The API starts on `http://localhost:8081` (see `src/main/resources/application.yml`).

## Railway Deployment

1) Create a PostgreSQL database on Railway.
2) Set these variables in Railway:

- `DATABASE_URL` (JDBC format, e.g. `jdbc:postgresql://HOST:PORT/DB`)
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`

3) Redeploy the service.

## Default Seed Users

Seed data is created at startup in `src/main/java/com/smartschool/api/config/DataInitializer.java`.

- Admin: `admin@school.com` / `admin123`
- Teacher: `turing@school.com` / `password123`
- Student: `chamrong@school.com` / `password123`

## API Auth

### Login

- `POST /api/auth/login`

Example request:

```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"admin@school.com\",\"password\":\"admin123\"}"
```

Response:

```json
{
  "token": "<jwt>",
  "id": 1,
  "email": "admin@school.com",
  "role": "ADMIN"
}
```

## Build

```powershell
D:\smart-school-api\mvnw.cmd clean package -DskipTests
```

## Notes

- If login fails with "invalid", confirm the backend is pointing to the correct database and that the seed data exists.
- For frontend calls, use the Railway URL directly (not as a path under Vercel).

