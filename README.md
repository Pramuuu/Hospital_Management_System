# Hospital Management System

A comprehensive Spring Boot–based application to streamline hospital operations, including patient, staff, department, and appointment management with secure authentication and robust error handling.

---

## 🚀 Features

* **Patient Management**: Create, update, view, and delete patient records with demographic details.
* **Staff & Department Management**: Register staff, assign roles and departments, and manage departmental hierarchy.
* **Appointment Scheduling**: Book, reschedule, and cancel appointments with conflict prevention and buffer time.
* **Security**: JWT-based authentication, BCrypt password hashing, and role-based access control.
* **Error Handling**: Clear JSON responses for validation errors, conflicts, resource not found, and unauthorized access.
* **In-Memory Development DB**: H2 database for rapid prototyping and testing, with seamless migration to external databases.

---

## 🛠️ Technology Stack

* **Backend**: Java, Spring Boot 3.4.0, Spring MVC, Spring Security
* **Persistence**: JPA/Hibernate, H2 (in-memory database)
* **Build & Dependency**: Maven
* **Authentication**: JWT, BCryptPasswordEncoder
* **Version Control**: Git, GitHub
* **IDE**: Visual Studio Code
* **Testing**: JUnit, Spring Boot Test

---

## 📥 Getting Started

### Prerequisites

* Java 17 or higher
* Maven 3.6+
* Git

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/Pramuuu/Hospital_Management_System.git
   cd Hospital_Management_System/HMS
   ```

2. **Build the project**

   ```bash
   mvn clean install
   ```

3. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**

   * Default port: `http://localhost:8080`
   * H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

---

## 📚 API Reference

### Patient Endpoints

| Method | URI                  | Description           |
| ------ | -------------------- | --------------------- |
| POST   | `/api/patients`      | Create a new patient  |
| GET    | `/api/patients/{id}` | Get patient by ID     |
| PUT    | `/api/patients/{id}` | Update patient record |
| DELETE | `/api/patients/{id}` | Delete patient        |

### Staff Endpoints

| Method | URI               | Description               |
| ------ | ----------------- | ------------------------- |
| POST   | `/api/staff`      | Register new staff member |
| GET    | `/api/staff/{id}` | Get staff member by ID    |
| PUT    | `/api/staff/{id}` | Update staff details      |
| DELETE | `/api/staff/{id}` | Remove staff member       |

### Department Endpoints

| Method | URI                     | Description               |
| ------ | ----------------------- | ------------------------- |
| POST   | `/api/departments`      | Create a new department   |
| GET    | `/api/departments/{id}` | Get department by ID      |
| PUT    | `/api/departments/{id}` | Update department details |
| DELETE | `/api/departments/{id}` | Delete department         |

### Appointment Endpoints

| Method | URI                      | Description                        |
| ------ | ------------------------ | ---------------------------------- |
| POST   | `/api/appointments`      | Schedule a new appointment         |
| GET    | `/api/appointments/{id}` | Retrieve appointment details       |
| PUT    | `/api/appointments/{id}` | Reschedule an existing appointment |
| DELETE | `/api/appointments/{id}` | Cancel an appointment              |

---

## 🔒 Authentication & Security

* **JWT Tokens**: Obtain by logging in via `/api/auth/login`.
* **Password Hashing**: BCrypt with configurable strength.
* **Role-Based Access**: Guards sensitive endpoints per user roles (ADMIN, DOCTOR, STAFF).

---

## ⚙️ Error Handling

Standardized JSON error responses:

```json
{
  "success": false,
  "message": "Resource not found",
  "details": null
}
```

* **Validation Errors**: Missing or invalid fields.
* **Conflict Errors**: Time slot overlaps.
* **Authentication Errors**: Invalid credentials or expired tokens.

---

## 📂 Data Model

* **Patient**: One-to-Many → Appointments
* **Department**: One-to-Many → Staff
* **Appointment**: Many-to-One → Patient, Doctor

Relationships managed via JPA annotations for referential integrity.

---

## 🤝 Contributing

Contributions are welcome! Please fork the repo and submit a pull request with descriptive commits.

---

## 📄 License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

