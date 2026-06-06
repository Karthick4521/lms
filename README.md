# Library Management System

A full-stack Library Management System built with Spring Boot, MySQL, HTML, CSS, and JavaScript.

## Technologies Used
- **Backend:** Java 21, Spring Boot 3.3.2
- **Database:** MySQL 8.0, Spring Data JPA, Hibernate
- **Frontend:** HTML5, CSS3, JavaScript
- **Build Tool:** Gradle
- **Version Control:** Git & GitHub

## Features
- Add, View, Delete Books
- Add, View, Delete Members
- Issue Books to Members
- Return Books
- Track Overdue Books
- Dashboard with Statistics

## How to Run
1. Install Java 21 and MySQL 8.0
2. Create database: `library_management`
3. Update `application.properties` with your MySQL password
4. Run: `.\gradlew bootRun`
5. Open: `http://localhost:8080`

## API Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/books | Get all books |
| POST | /api/books | Add new book |
| DELETE | /api/books/{id} | Delete book |
| GET | /api/members | Get all members |
| POST | /api/members | Add new member |
| POST | /api/borrow/issue | Issue book |
| PUT | /api/borrow/return/{id} | Return book |

## Developer
- **Name:** Dinesh (Karthick4521)
- **College:** 3rd Year CSE Student