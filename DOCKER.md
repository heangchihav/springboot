# Docker Quick Start Guide

## Quick Commands

### Start Everything
```bash
docker-compose up --build
```

### Start in Background
```bash
docker-compose up -d
```

### View Logs
```bash
# All services
docker-compose logs -f

# Application only
docker-compose logs -f app

# Database only
docker-compose logs -f postgres
```

### Stop Services
```bash
docker-compose down
```

### Stop and Remove Volumes (Clean Database)
```bash
docker-compose down -v
```

### Rebuild After Code Changes
```bash
docker-compose up --build
```

## Development Mode

For development with hot reload and debug port:

```bash
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
```

## Database Access

### Connect from Host Machine
```bash
psql -h localhost -p 5432 -U postgres -d demo_db
```

### Connect from Another Container
```bash
docker exec -it demo-postgres psql -U postgres -d demo_db
```

### View Database Logs
```bash
docker-compose logs postgres
```

## Troubleshooting

### Application Won't Start
1. Check if database is healthy: `docker-compose ps`
2. Check application logs: `docker-compose logs app`
3. Verify environment variables in `docker-compose.yml`

### Database Connection Issues
1. Ensure PostgreSQL container is running: `docker-compose ps postgres`
2. Check database health: `docker-compose exec postgres pg_isready -U postgres`
3. Verify credentials match in `docker-compose.yml` and `application-docker.yml`

### Port Already in Use
If port 8080 or 5432 is already in use, modify ports in `docker-compose.yml`:
```yaml
ports:
  - "8081:8080"  # Change host port
```

### Reset Everything
```bash
# Stop and remove containers, networks, and volumes
docker-compose down -v

# Remove images
docker-compose down --rmi all

# Start fresh
docker-compose up --build
```

## Environment Variables

You can override default values by creating a `.env` file:

```env
DB_NAME=demo_db
DB_USERNAME=postgres
DB_PASSWORD=your_password
DB_PORT=5432
APP_PORT=8080
JWT_SECRET=your-secret-key
SPRING_PROFILES_ACTIVE=docker
```

## Health Checks

- **Application**: `http://localhost:8080/api/health`
- **Database**: Automatically checked by Docker Compose

## Production Deployment

For production, consider:
1. Using environment variables for sensitive data
2. Setting up proper secrets management
3. Using production-grade PostgreSQL configuration
4. Enabling SSL/TLS for database connections
5. Setting up proper backup strategies

