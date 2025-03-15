# Expense Tracker API

## Overview
The Expense Tracker API is a RESTful API built with **Spring Boot** and **PostgreSQL**, allowing users to manage their expenses securely. Users can sign up, log in, and track their expenses in different categories. The API provides filtering options for past expenses and ensures data security with JWT authentication.

## Features
- **User Authentication**
  - Sign up as a new user
  - Log in and receive a JWT token
  - Secure endpoints with JWT authentication
- **Expense Management**
  - Add new expenses
  - View all expenses
  - Update existing expenses
  - Delete expenses
- **Expense Categories**
  - Groceries
  - Leisure
  - Electronics
  - Utilities
  - Clothing
  - Health
  - Others

## Technology Stack
- **Backend:** Spring Boot
- **Database:** PostgreSQL
- **Authentication:** JSON Web Tokens (JWT)
- **ORM:** Hibernate (JPA)

## Installation and Setup

### Prerequisites
- Ensure you have [Java](https://www.oracle.com/java/) and [PostgreSQL](https://www.postgresql.org/) installed.
- Set up a PostgreSQL database and configure the credentials.

### Steps to Set Up
1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/expense-tracker-api.git
   cd expense-tracker-api
   ```
2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
   jwt.secret=your-secret-key
   ```
3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints
### Authentication
| Method | Endpoint        | Description       |
|--------|---------------|-------------------|
| POST   | /api/v1/auth/register | Register a user   |
| POST   | /api/v1/auth/login  | Authenticate user |

### Expense Management
| Method | Endpoint           | Description                          |
|--------|------------------|--------------------------------------|
| GET    | /api/v1/expenses    | Get all user expenses               |
| POST   | /api/v1/expenses    | Add a new expense                   |
| PUT    | /api/v1/expenses/:id | Update an existing expense          |
| DELETE | /api/v1/expenses/:id | Delete an expense                   |

## Security & Authentication
- Uses **JWT tokens** to protect endpoints.
- Users can only access their own expenses.
- Passwords are hashed using **BCrypt** for secure storage.

## Contributing
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m "Add feature"`).
4. Push to the branch (`git push origin feature-name`).
5. Open a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
For any issues or inquiries, please reach out via GitHub Issues or email at `azamatabraev07@example.com`.

