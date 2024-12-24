# Record Shop API

The **Record Shop API** provides a robust backend for managing a record shop's inventory.
It supports full CRUD operations for albums, along with additional features like API health checks and advanced
filtering of albums based on various criteria.

Built with **Java** and **Spring Boot**, the application emphasises clean architecture,
best practices, and a scalable design.


---

## Features
1. List all albums in the shop's inventory
2. Create a new album to add to the shop's inventory
3. Retrieve an album based on album ID
4. Update album details based on album ID
5. Delete an album based on album ID
6. Search for albums based on a combination of optional parameters (artist, release year, genre, album name)
7. Health check for monitoring
8. Configurable with PostgreSQL (production) or H2 in-memory database (development).
9. Request validation for POST and PUT operations, with feedback for correcting malformed data.
10. Backed by a robust suite of automated tests

---

## Technologies Used
- **Backend**: Java 21, Spring Boot 3.2.1
- **Database**: PostgreSQL (production), H2 (development)
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, MockMvc
- **Other Tools**: Lombok

---

## Setup Instructions

### Prerequisites
- Java 21 installed
- Maven installed
- PostgreSQL installed and running locally (optional for production)

### Steps
1. Clone the repository:
   ```
   git clone https://github.com/SeanWin/RecordShop.git
   cd RecordShop
   ```
2. Open project in preferred IDE (eg IntelliJ)
3. (optional, if you plan to use PostgreSQL) 
   - Update `application-prod.properties` file with your username and password 
   -  Set `spring.profiles.active=prod` in `application.properties` file.
4. Run the application
5. Access the API
   - Base URL  http://localhost:8080

---
## API Endpoints


| Method | Endpoint                           | Description                                                                                |
|--------|------------------------------------|--------------------------------------------------------------------------------------------|
| GET    | `/api/v1/recordshop/albums`        | List all albums                                                                            |
| GET    | `/api/v1/recordshop/albums/{id}`   | Get an album by ID                                                                         |
| POST   | `/api/v1/recordshop/albums`        | Add a new album                                                                            |
| PUT    | `/api/v1/recordshop/albums/{id}`   | Update details of an existing album by its ID                                              |
| DELETE | `/api/v1/recordshop/albums/{id}`   | Delete an album by ID.                                                                     |
| GET    | `/api/v1/recordshop/albums/search` | Search for albums using optional parameters of artist, release year, genre, or album name. |
| GET    | `/actuator/health`                 | Perform a health check to verify that the application is running.                          | 
 

---
## Future Enhancements
- containerise using Docker
- deploy the application
- swagger for API documentation