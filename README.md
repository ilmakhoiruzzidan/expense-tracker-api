# **Expense Tracker API**

This project is a **Spring Boot-based Expense Tracker API** designed to manage users, categories, authentication, and
expenses. It provides secure endpoints for managing users, categories, and transactions and uses JWT for authentication
and role-based access control.

---

# ERD

![expensetracker](https://github.com/user-attachments/assets/cbdf9695-78e1-4394-a67a-305c26e87db6)

## **Features**

- **User Management**: Register, login, and manage user information.
- **Category Management**: CRUD operations for expense categories.
- **Authentication**: JWT-based authentication with login, refresh token, and logout endpoints.
- **Role-Based Access Control**: Authorization for admin and user roles.
- **Pagination and Sorting**: API endpoints support pagination and sorting for retrieving data.
- **Docker Support**: Deployable with Docker.

---

## **Technologies Used**

- **Spring Boot**: Framework for creating RESTful APIs.
- **Spring Security**: For authentication and authorization.
- **Hibernate (JPA)**: For database interaction.
- **PostgreSQL**: Database for storing user and category data.
- **Docker**: For containerizing the application.
- **Maven**: Dependency management and build tool.

---

## **Prerequisites**

- **Java 17**
- **Maven**
- **Docker** (optional, for containerized deployment)
- **PostgreSQL**

---

## **API Endpoints**

### **Authentication**

| **Method** | **Endpoint**              | **Description**                  |
|------------|---------------------------|----------------------------------|
| POST       | `/api/auth/login`         | Login with username and password |
| POST       | `/api/auth/register`      | Register a new user              |
| POST       | `/api/auth/refresh-token` | Refresh the access token         |
| POST       | `/api/auth/logout`        | Logout and invalidate tokens     |

---

### **User Management**

| **Method** | **Endpoint**                      | **Description**            |
|------------|-----------------------------------|----------------------------|
| GET        | `/api/users/me`                   | Retrieve current user info |
| POST       | `/api/users`                      | Create a new user          |
| PATCH      | `/api/users/{id}/update-password` | Update user password       |

---

### **Category Management**

| **Method** | **Endpoint**           | **Description**             |      
|------------|------------------------|-----------------------------|
| GET        | `/api/categories`      | Retrieve all categories     | 
| GET        | `/api/categories/{id}` | Retrieve a category by ID   | 
| POST       | `/api/categories`      | Create a new category       | 
| PUT        | `/api/categories/{id}` | Update an existing category | 
| DELETE     | `/api/categories/{id}` | Delete a category           | 

---

### **Expense Management**

| **Method** | **Endpoint**         | **Description**           | 
|------------|----------------------|---------------------------|
| GET        | `/api/expenses`      | Retrieve all expenses     |
| GET        | `/api/expenses/{id}` | Retrieve an expense by ID |
| POST       | `/api/expenses`      | Create a new expense      |
| PUT        | `/api/expenses/{id}` | Update an expense by ID   |
| DELETE     | `/api/expenses/{id}` | Delete an expense by ID   |

---

### **Pagination and Sorting**

- **Query Parameters**:
    - `page`: Page number (default: `1`)
    - `size`: Number of items per page (default: `10`)
    - `sortBy`: Field to sort by (e.g., `name`, `createdAt`)
    - **Example**:
      ```
      GET /api/categories?page=2&size=5&sortBy=name
      ```

---

### **Error Responses**

- **Standard Error Response Format**:

```json
{
  "timestamp": "2024-11-17T10:45:23",
  "status": 400,
  "error": "Bad Request",
  "message": "Specific error message here",
  "path": "/api/some-endpoint"
}
```

