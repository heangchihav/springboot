# Docker Configuration

This directory contains Docker-related configuration files.

## Structure

```
docker/
└── postgres/
    └── init.sql          # Database initialization script
```

## Database Initialization

The `init.sql` script runs automatically when the PostgreSQL container is first created. You can use it to:

- Create database extensions
- Set up initial schema
- Insert seed data

**Note**: Spring Boot JPA will automatically create tables based on your entity classes if `spring.jpa.hibernate.ddl-auto` is set to `update` or `create`.

## Customization

To customize the database initialization:

1. Edit `docker/postgres/init.sql`
2. Recreate the container: `docker-compose down -v && docker-compose up`

