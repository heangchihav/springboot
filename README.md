# Spring Boot Demo Project

A professional Spring Boot application with a clean, standard folder structure.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST Controllers
│   │       ├── service/         # Business logic layer
│   │       │   └── impl/        # Service implementations
│   │       ├── repository/      # Data access layer (JPA Repositories)
│   │       ├── model/           # Entity models
│   │       │   └── entity/      # JPA entities
│   │       ├── dto/             # Data Transfer Objects
│   │       │   └── response/   # Response DTOs
│   │       ├── exception/       # Custom exceptions and handlers
│   │       ├── security/        # Security configuration
│   │       └── util/            # Utility classes
│   └── resources/
│       ├── application.yml      # Main configuration
│       ├── application-dev.yml  # Development profile
│       ├── application-prod.yml # Production profile
│       ├── static/             # Static resources (CSS, JS, images)
│       └── templates/          # Templates (if using Thymeleaf)
└── test/
    └── java/
        └── com/example/demo/
            ├── controller/      # Controller tests
            └── service/         # Service tests
```

## Features

- **Clean Architecture**: Separation of concerns with layered architecture
- **RESTful API**: Standard REST endpoints
- **Security**: Spring Security integration
- **Database**: PostgreSQL with JPA/Hibernate
- **Docker Support**: Complete Docker and Docker Compose setup
- **Exception Handling**: Global exception handler
- **Configuration**: Environment-specific configurations
- **Testing**: Unit and integration test structure

## Getting Started

### Prerequisites

- Java 24
- Maven 3.6+
- Docker and Docker Compose (for containerized setup)
- PostgreSQL (for local development without Docker)

### Running with Docker (Recommended)

The easiest way to run the application is using Docker Compose:

1. **Build and start all services** (PostgreSQL + Spring Boot app):
   ```bash
   docker-compose up --build
   ```

2. **Run in detached mode**:
   ```bash
   docker-compose up -d
   ```

3. **View logs**:
   ```bash
   docker-compose logs -f app
   ```

4. **Stop services**:
   ```bash
   docker-compose down
   ```

5. **Stop and remove volumes** (clean database):
   ```bash
   docker-compose down -v
   ```

### Running Locally (Without Docker)

1. **Start PostgreSQL database** (if not using Docker):
   ```bash
   # Using Docker for database only
   docker run -d --name demo-postgres \
     -e POSTGRES_DB=demo_db \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=postgres \
     -p 5432:5432 \
     postgres:16-alpine
   ```

2. **Update database credentials** in `application.yml` if needed

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

### Environment Variables

Create a `.env` file in the root directory (optional):

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=demo_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
APP_PORT=8080
SPRING_PROFILES_ACTIVE=docker
JWT_SECRET=your-secret-key-change-in-production
```

### Running Tests

```bash
mvn test
```

## Docker Commands

### Build Docker Image
```bash
docker build -t demo-app .
```

### Run Container
```bash
docker run -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  demo-app
```

### Development Mode
```bash
# Use development override
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
```

## Configuration

### Spring Profiles

- **Default**: Uses `application.yml`
- **Development**: `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
- **Production**: `mvn spring-boot:run -Dspring-boot.run.profiles=prod`
- **Docker**: Automatically activated when running in Docker

### Database Configuration

The application supports multiple database configurations:
- **Local**: `application.yml` (default)
- **Docker**: `application-docker.yml` (auto-activated in containers)
- **Development**: `application-dev.yml`
- **Production**: `application-prod.yml`

## API Endpoints

- **Health Check**: `GET http://localhost:8080/api/health`

## Docker Services

When running with `docker-compose up`, the following services are available:

- **Application**: `http://localhost:8080`
- **PostgreSQL**: `localhost:5432`
  - Database: `demo_db` (default)
  - Username: `postgres` (default)
  - Password: `postgres` (default)

## Database Connection

The application uses HikariCP connection pooling with the following default settings:
- Maximum pool size: 10
- Minimum idle: 5
- Connection timeout: 30 seconds
- Idle timeout: 10 minutes
- Max lifetime: 30 minutes

## Best Practices

1. **Controllers**: Handle HTTP requests/responses only
2. **Services**: Contain business logic
3. **Repositories**: Handle data access
4. **DTOs**: Use for data transfer between layers
5. **Entities**: Represent database tables
6. **Exceptions**: Custom exceptions for better error handling

