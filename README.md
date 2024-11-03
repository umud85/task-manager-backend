# Task Manager App

## Description
This is a simple task manager application built with Spring Boot and MySQL. For practice purposes, I chose to implement the database interactions using the low-level `java.sql` API instead of Spring Data or other higher-level abstractions. This approach allows me to deepen my understanding of how database connections, statements, and result sets work in Java.

## Prerequisites
- Java 17 or higher
- MySQL Server
- Maven (for building the project)

## Setup Instructions

### Database Setup
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd task-manager-app
   ```

2. **Create the Database**:
   ```mysql
   CREATE DATABASE task_manager;
   ```

3. **Set Environment Variables**:
    - Set the following environment variables:
   ```bash
   export DB_USER=root         # Your MySQL username
   export MYSQL_PASSWORD=your_password  # Your MySQL password
   ```

### Running the Application
1. **Build the Project**:
   ```bash
   ./mvnw clean install
   ```

2. **Run the Application**:
   ```bash
   ./mvnw spring-boot:run
   ```

### Troubleshooting
- If you encounter the error "unknown database 'task_manager'", ensure that you have created the database as mentioned above.
- Make sure that the MySQL server is running and accessible.

### License
This project is licensed under the MIT License.