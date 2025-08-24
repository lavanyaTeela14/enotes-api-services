# 📒 eNotes API Services

## 📌 Overview
The **eNotes API Services** project is a backend application built using **Spring Boot** that provides a complete RESTful API for managing electronic notes.  
It supports **user management, authentication, categories, and note CRUD operations**, enabling a digital note-taking platform that can be integrated with any frontend (web/mobile).

---

## ⚙️ Tech Stack
- **Java 17+**
- **Spring Boot** (Web, Security, Validation, JPA)
- **Hibernate / JPA** for ORM
- **MySQL** as Database
- **Spring Security with JWT** for authentication & authorization
- **Lombok** for boilerplate code reduction
- **Maven** for dependency management
- **Postman** / **Swagger** for API testing

---

## 🗂️ Project Structure
enotes-api-services/
│── src/main/java/com/example/enotes/
│ ├── controller/ # REST controllers (API endpoints)
│ ├── dto/ # Data Transfer Objects
│ ├── entity/ # JPA Entities (User, Notes, Category, etc.)
│ ├── exception/ # Global exception handling
│ ├── handler/ # Response handling classes
│ ├── repository/ # Spring Data JPA Repositories
│ ├── security/ # JWT, Authentication & Authorization
│ ├── service/ # Business logic layer
│ └── EnotesApiServicesApplication.java # Main class
│
│── src/main/resources/
│ ├── application.properties # DB & server configurations
│
│── pom.xml # Maven dependencies
│── README.md # Project documentation


---

## 🚀 Features
✅ **User Management** – Register, Login, Update Profile  
✅ **Authentication** – JWT-based secure login & access  
✅ **Notes Management** – Create, Read, Update, Delete notes  
✅ **Categories** – Group notes into categories  
✅ **Validation** – Input validation for secure APIs  
✅ **Error Handling** – Global exception handling with custom responses  
✅ **Role-based Access** – Admin & User separation  

---

## 🔑 API Endpoints

### 🔐 Authentication
| Method | Endpoint              | Description             |
|--------|----------------------|-------------------------|
| POST   | `/api/auth/register` | Register a new user |
| POST   | `/api/auth/login`    | Login & get JWT token |

### 👤 User Management
| Method | Endpoint          | Description             |
|--------|------------------|-------------------------|
| GET    | `/api/users/{id}`    | Get user details by ID |
| PUT    | `/api/users/{id}`    | Update user profile |
| DELETE | `/api/users/{id}`    | Delete user account |

### 📝 Notes Management
| Method | Endpoint             | Description                 |
|--------|---------------------|-----------------------------|
| GET    | `/api/notes`        | Get all notes for logged-in user |
| GET    | `/api/notes/{id}`   | Get note by ID |
| POST   | `/api/notes`        | Create a new note |
| PUT    | `/api/notes/{id}`   | Update note by ID |
| DELETE | `/api/notes/{id}`   | Delete note by ID |

### 📂 Category Management
| Method | Endpoint              | Description                 |
|--------|----------------------|-----------------------------|
| GET    | `/api/categories`    | Get all categories |
| POST   | `/api/categories`    | Create a category |
| PUT    | `/api/categories/{id}` | Update category |
| DELETE | `/api/categories/{id}` | Delete category |

---

## ⚡ Setup & Installation

### 🔹 Prerequisites
- Install **Java 17+**
- Install **Maven 3.6+**
- Install **MySQL** and create a database (example: `enotes_db`)

---

### 🔹 Clone the Repository
```bash
git clone https://github.com/lavanyaTeela14/enotes-api-services.git
cd enotes-api-services

---

### 🔹 Configure Database
src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/enotes_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
jwt.secret=yourSecretKey

---

🔹 Build & Run
mvn clean install
mvn spring-boot:run

---

App will start at:
👉 http://localhost:8080

---

🧪 Testing

Use Postman collection (enotes-api-collection.json) to test endpoints

Or open Swagger UI (if configured) at:
👉 http://localhost:8080/swagger-ui.html
mvn test

---

📬 Response Format
All responses follow a standardized structure:
{
  "status": "success",
  "message": "Note created successfully",
  "data": {
    "id": 1,
    "title": "First Note",
    "content": "This is my first note."
  }
}

---

🔄 API Testing Workflow
Here’s the recommended sequence for testing the APIs in Postman:
Register a user → POST /api/auth/register
Login → POST /api/auth/login → copy jwtToken
Create a category → POST /api/categories
Create a note → POST /api/notes
Fetch all notes → GET /api/notes
Update a note → PUT /api/notes/{id}
Delete a note → DELETE /api/notes/{id}


