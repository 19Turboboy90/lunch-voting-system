# Lunch Voting System

This project is a RESTful server application.
The system allows administrators to manage restaurants and menus, and users can select the restaurant where they would
like to dine. Voting is limited in time.

___

## Features

- User registration and authentication
- Role-based access control (USER/ADMINISTRATOR)
- Restaurant and menu management (administrator)
- Daily voting for a restaurant with time limits
- REST API with error checking and handling

___

## Technology Stack

- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA (Hibernate)
- REST API
- H2
- Swagger / OpenAPI
- JUnit 5
- MockMvc
- Maven
- Lombok
- Caffeine Cache

___

## Business problem

For many, choosing a place where you can have a delicious lunch with a pleasant atmosphere is a difficult process.
This system solves the problem by allowing you to vote for available restaurants. Voting takes place every day, which
ensures transparency and fairness in decision-making.
___

## Requirements

- Two types of users: ** ADMINISTRATOR** and ** USER**
- Administrators can create restaurants and manage daily lunch menus
- Every restaurant offers a new menu every day
- The menu consists of 2-5 items (the name of the dish and the price).
- Regular users can vote for the restaurant for the current day
- Only one vote per user per day is counted.
- If user votes again the same day:
    - Until **11:00** — the user can change his vote
    - After **11:00** — voting changes are not allowed

___

### Swagger / OpenAPI Documentation

The REST API documentation is available when the application is running:
[User Interface - Swagger](http://localhost:8080/swagger-ui/index.html)

**Test credentials:**

```
Admin: admin@gmail.com / admin 
User:  user@yandex.ru / password 
Guest: guest@gmail.com / guest
```