-- Database initialization script
-- This script runs when the PostgreSQL container is first created

-- Create extensions if needed
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- You can add initial schema or data here
-- Example:
-- CREATE TABLE IF NOT EXISTS example_table (
--     id BIGSERIAL PRIMARY KEY,
--     name VARCHAR(255) NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

-- Note: Spring Boot JPA will create tables automatically based on entities
-- if spring.jpa.hibernate.ddl-auto is set to 'update' or 'create'

