# Lunch Voting System

This project is a RESTful backend application designed to manage lunch voting in a corporate environment.
The system allows administrators to manage restaurants and menus, while users can vote for available lunch options
within a limited time window.

___

## Features

- User registration and authentication
- Role-based access control (USER / ADMIN)
- Restaurant and menu management (admin)
- Daily lunch voting with time restrictions
- Vote updates until the deadline
- REST API with validation and error handling

___

## Technology Stack

- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA (Hibernate)
- REST API
- H2 (dev)
- Swagger / OpenAPI
- JUnit 5
- MockMvc
- Maven
- Lombok
- Caffeine Cache

___

## Original Task Description

Design and implement a REST API using Hibernate / Spring / Spring MVC
(Spring Boot preferred) without a frontend.
___

## Business Problem

In many teams, choosing a place for lunch is a repetitive and time-consuming process.
This system solves the problem by allowing employees to vote for available lunch options
within a defined time window, ensuring a transparent and fair decision.
___

## Requirements

- Two types of users: **ADMIN** and **USER**
- Admins can create restaurants and manage daily lunch menus
- Each restaurant provides a new menu every day
- A menu consists of 2–5 items (dish name and price)
- Regular users can vote for a restaurant for the current day
- Only one vote per user is counted per day
- Vote update rules:
    - Before **11:00** — the user can change their vote
    - After **11:00** — vote changes are not allowed

___

### Swagger / OpenAPI Documentation

The REST API documentation is available when the application is running:
[Swagger UI](http://localhost:8080/swagger-ui/index.html)

**Test credentials:**

```
Admin: admin@gmail.com / admin 
User:  user@yandex.ru / password 
Guest: guest@gmail.com / guest
```