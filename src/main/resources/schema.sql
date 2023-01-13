CREATE SCHEMA IF NOT EXISTS iis_schema;

CREATE TABLE IF NOT EXISTS iis_schema.users_info
(
    id       bigserial NOT NULL,
    name     varchar(50) NOT NULL,
    surname  varchar(50) NOT NULL,
    email    varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    role     varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS iis_schema.teachers_info
(
    id      bigint NOT NULL,
    PRIMARY KEY (id),
    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES iis_schema.users_info (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS iis_schema.groups
(
    id     bigserial NOT NULL,
    PRIMARY KEY (id),
    number int NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS iis_schema.students_info
(
    id         bigserial NOT NULL,
    PRIMARY KEY (id),
    year       int NOT NULL,
    faculty    varchar(50) NOT NULL,
    speciality varchar(50) NOT NULL,
    user_id    bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES iis_schema.users_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    group_id   bigint NOT NULL,
    FOREIGN KEY (group_id) REFERENCES iis_schema.groups (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS iis_schema.subjects
(
    id   bigserial NOT NULL,
    PRIMARY KEY (id),
    name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS iis_schema.teachers_subjects
(
    teacher_id bigint NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES iis_schema.teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    subject_id bigint NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES iis_schema.subjects (id) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (teacher_id, subject_id)
);

CREATE TABLE IF NOT EXISTS iis_schema.lessons
(
    id         bigserial NOT NULL,
    PRIMARY KEY (id),
    room       int NOT NULL,
    weekday    varchar(50) NOT NULL,
    start_time time NOT NULL,
    end_time   time NOT NULL,
    subject_id bigint NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES iis_schema.subjects (id) ON UPDATE CASCADE ON DELETE CASCADE,
    group_id   bigint NOT NULL,
    FOREIGN KEY (group_id) REFERENCES iis_schema.groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    teacher_id bigint NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES iis_schema.teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS iis_schema.marks
(
    id         bigserial NOT NULL,
    PRIMARY KEY (id),
    date       date NOT NULL,
    value      int NOT NULL,
    student_id bigint NOT NULL,
    FOREIGN KEY (student_id) REFERENCES iis_schema.students_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    teacher_id bigint NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES iis_schema.teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    subject_id bigint NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES iis_schema.subjects (id) ON UPDATE CASCADE ON DELETE CASCADE
);