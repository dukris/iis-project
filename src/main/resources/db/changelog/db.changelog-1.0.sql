--liquibase formatted sql

--changeset hanna:1
CREATE SCHEMA IF NOT EXISTS iis;

SET SCHEMA 'iis';