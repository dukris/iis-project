--liquibase formatted sql

--changeset hanna:1
INSERT INTO users_info (name, surname, email, password, role)
VALUES ('Admin', 'Admin', 'admin', '$2a$10$XKqS0Ov/axluIjvQhBmv7uiWF6wNY/FOabwyA/CIMDjqtLH2mrRkW', 'ROLE_ADMIN')