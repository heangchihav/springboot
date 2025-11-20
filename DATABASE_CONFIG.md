# Database Configuration Guide

This guide shows you all the places where you can configure the database URL and connection settings.

## Configuration Locations

### 1. **YAML Configuration Files** (Recommended)

#### For Local Development
**File:** `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:demo_db}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
```

**To change the database URL directly:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/my_database
    username: myuser
    password: mypassword
```

#### For Docker Environment
**File:** `src/main/resources/application-docker.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST:postgres}:${DB_PORT:5432}/${DB_NAME:demo_db}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:postgres}
```

#### For Development Profile
**File:** `src/main/resources/application-dev.yml`

#### For Production Profile
**File:** `src/main/resources/application-prod.yml`

---

### 2. **Environment Variables** (Flexible)

You can override database settings using environment variables:

#### Windows PowerShell:
```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="5432"
$env:DB_NAME="my_database"
$env:DB_USERNAME="myuser"
$env:DB_PASSWORD="mypassword"
```

#### Windows CMD:
```cmd
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=my_database
set DB_USERNAME=myuser
set DB_PASSWORD=mypassword
```

#### Linux/Mac:
```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=my_database
export DB_USERNAME=myuser
export DB_PASSWORD=mypassword
```

---

### 3. **Docker Compose** (For Containerized Setup)

**File:** `docker-compose.yml`

```yaml
services:
  app:
    environment:
      DB_HOST: postgres          # Database hostname (use 'postgres' for Docker, 'localhost' for external)
      DB_PORT: 5432              # Database port
      DB_NAME: demo_db           # Database name
      DB_USERNAME: postgres      # Database username
      DB_PASSWORD: postgres      # Database password
```

**To use an external database:**
```yaml
services:
  app:
    environment:
      DB_HOST: host.docker.internal  # For Windows/Mac to access host machine
      # OR
      DB_HOST: 192.168.1.100         # External database IP
      DB_PORT: 5432
      DB_NAME: production_db
      DB_USERNAME: prod_user
      DB_PASSWORD: prod_password
```

---

### 4. **.env File** (For Docker Compose)

Create a `.env` file in the project root:

```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=demo_db
DB_USERNAME=postgres
DB_PASSWORD=postgres

# Application Configuration
APP_PORT=8080
SPRING_PROFILES_ACTIVE=docker
JWT_SECRET=your-secret-key
```

Docker Compose will automatically read these variables.

---

### 5. **System Properties** (Java Command Line)

```bash
java -jar app.jar \
  -Dspring.datasource.url=jdbc:postgresql://localhost:5432/my_db \
  -Dspring.datasource.username=myuser \
  -Dspring.datasource.password=mypassword
```

---

## Database URL Format

The PostgreSQL JDBC URL format is:
```
jdbc:postgresql://[host]:[port]/[database]
```

### Examples:

1. **Local database:**
   ```
   jdbc:postgresql://localhost:5432/demo_db
   ```

2. **Remote database:**
   ```
   jdbc:postgresql://192.168.1.100:5432/production_db
   ```

3. **With SSL:**
   ```
   jdbc:postgresql://localhost:5432/demo_db?ssl=true
   ```

4. **With additional parameters:**
   ```
   jdbc:postgresql://localhost:5432/demo_db?ssl=true&sslmode=require&connectTimeout=30
   ```

---

## Configuration Priority

Spring Boot reads configuration in this order (highest to lowest priority):

1. **Command-line arguments** (`-D` properties)
2. **Environment variables**
3. **application-{profile}.yml** (when profile is active)
4. **application.yml**
5. **Default values** (hardcoded in YAML)

---

## Quick Examples

### Example 1: Change to Different Local Database

Edit `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/my_new_database
    username: myuser
    password: mypass
```

### Example 2: Use Environment Variables

Set environment variables, then run:
```bash
mvn spring-boot:run
```

### Example 3: Use External Database with Docker

Edit `docker-compose.yml`:
```yaml
services:
  app:
    environment:
      DB_HOST: your-database-server.com
      DB_PORT: 5432
      DB_NAME: production_db
      DB_USERNAME: prod_user
      DB_PASSWORD: secure_password
```

Remove the `postgres` service if using external database.

---

## Testing Database Connection

After configuring, test the connection:

1. **Check application logs** for connection messages
2. **Use the health endpoint:** `GET http://localhost:8080/api/health`
3. **Check database directly:**
   ```bash
   psql -h localhost -p 5432 -U postgres -d demo_db
   ```

---

## Common Issues

### Issue: Cannot connect to database
- **Check if PostgreSQL is running**
- **Verify host, port, database name**
- **Check firewall settings**
- **Verify username and password**

### Issue: Connection timeout in Docker
- **Use service name** (`postgres`) instead of `localhost` when connecting from app container
- **Check network configuration** in docker-compose.yml
- **Verify `depends_on`** condition is met

### Issue: Database not found
- **Create the database** if it doesn't exist:
  ```sql
  CREATE DATABASE demo_db;
  ```
- **Or let Spring Boot create it** by setting `spring.jpa.hibernate.ddl-auto=create`

