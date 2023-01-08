CREATE SCHEMA IF NOT EXISTS iis_schema;

CREATE TABLE IF NOT EXISTS iis_schema.users
(
    id       bigserial    NOT NULL,
    name     varchar(50)  NOT NULL,
    surname  varchar(50)  NOT NULL,
    email    varchar(50)  NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    role     varchar(50)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS iis_schema.teachers_info
(
    id      bigserial NOT NULL,
    user_id bigserial NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES iis_schema.users (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS iis_schema.groups
(
    id     bigserial NOT NULL,
    number int       NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS iis_schema.students_info
(
    id       bigserial NOT NULL,
    year     int NOT NULL,
    faculty  varchar(50) NOT NULL,
    speciality  varchar(50) NOT NULL,
    user_id  bigserial NOT NULL,
    group_id bigserial NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES iis_schema.users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES iis_schema.groups (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS iis_schema.subjects
(
    id   bigserial   NOT NULL,
    name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS iis_schema.teachers_subjects
(
    teacher_id bigserial NOT NULL,
    subject_id bigserial NOT NULL,
    PRIMARY KEY (teacher_id, subject_id),
    FOREIGN KEY (teacher_id) REFERENCES iis_schema.teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES iis_schema.subjects (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS iis_schema.lessons
(
    id         bigserial   NOT NULL,
    room       int         NOT NULL,
    weekday    varchar(50) NOT NULL,
    start_time time        NOT NULL,
    end_time   time        NOT NULL,
    subject_id bigserial   NOT NULL,
    group_id   bigserial   NOT NULL,
    teacher_id bigserial   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (subject_id) REFERENCES iis_schema.subjects (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES iis_schema.groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES iis_schema.teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS iis_schema.marks
(
    id         bigserial NOT NULL,
    date       date      NOT NULL,
    value      int       NOT NULL,
    student_id bigserial NOT NULL,
    teacher_id bigserial NOT NULL,
    subject_id bigserial NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES iis_schema.students_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES iis_schema.subjects (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES iis_schema.teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE
);




-- CREATE TABLE IF NOT EXISTS users
-- (
--     id       bigserial    NOT NULL,
--     name     varchar(50)  NOT NULL,
--     surname  varchar(50)  NOT NULL,
--     email    varchar(50)  NOT NULL UNIQUE,
--     password varchar(100) NOT NULL,
--     role     varchar(50)  NOT NULL,
--     PRIMARY KEY (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS teachers_info
-- (
--     id      bigserial NOT NULL,
--     user_id bigserial NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (user_id) REFERENCES users (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS groups
-- (
--     id     bigserial NOT NULL,
--     number int       NOT NULL UNIQUE,
--     PRIMARY KEY (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS students_info
-- (
--     id       bigserial NOT NULL,
--     user_id  bigserial NOT NULL,
--     group_id bigserial NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (user_id) REFERENCES users (id) ,
--     FOREIGN KEY (group_id) REFERENCES groups (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS subjects
-- (
--     id   bigserial   NOT NULL,
--     name varchar(50) NOT NULL UNIQUE,
--     PRIMARY KEY (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS teachers_subjects
-- (
--     teacher_id bigserial NOT NULL,
--     subject_id bigserial NOT NULL,
--     PRIMARY KEY (teacher_id, subject_id),
--     FOREIGN KEY (teacher_id) REFERENCES teachers_info (id),
--     FOREIGN KEY (subject_id) REFERENCES subjects (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS lessons
-- (
--     id         bigserial   NOT NULL,
--     room       int         NOT NULL,
--     weekday    varchar(50) NOT NULL,
--     start_time time        NOT NULL,
--     end_time   time        NOT NULL,
--     subject_id bigserial   NOT NULL,
--     group_id   bigserial   NOT NULL,
--     teacher_id bigserial   NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (subject_id) REFERENCES subjects (id) ,
--     FOREIGN KEY (group_id) REFERENCES groups (id),
--     FOREIGN KEY (teacher_id) REFERENCES teachers_info (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS marks
-- (
--     id         bigserial NOT NULL,
--     date       date      NOT NULL,
--     value      int       NOT NULL,
--     student_id bigserial NOT NULL,
--     teacher_id bigserial NOT NULL,
--     subject_id bigserial NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (student_id) REFERENCES students_info (id) ,
--     FOREIGN KEY (subject_id) REFERENCES subjects (id) ,
--     FOREIGN KEY (teacher_id) REFERENCES teachers_info (id)
-- );