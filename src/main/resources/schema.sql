CREATE SCHEMA IF NOT EXISTS iis;

SET SCHEMA 'iis';

CREATE TABLE IF NOT EXISTS users_info
(
    id       bigserial NOT NULL,
    name     varchar(50) NOT NULL,
    surname  varchar(50) NOT NULL,
    email    varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    role     varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS teachers_info
(
    id      bigint NOT NULL,
    user_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users_info (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS groups
(
    id     bigserial NOT NULL,
    number int NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS students_info
(
    id         bigserial NOT NULL,
    year       int NOT NULL,
    faculty    varchar(50) NOT NULL,
    speciality varchar(50) NOT NULL,
    user_id    bigint NOT NULL,
    group_id   bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subjects
(
    id   bigserial NOT NULL,
    name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS teachers_subjects
(
    teacher_id bigint NOT NULL,
    subject_id bigint NOT NULL,
    PRIMARY KEY (teacher_id, subject_id),
    CONSTRAINT fk_teacher FOREIGN KEY (teacher_id) REFERENCES teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_subject FOREIGN KEY (subject_id) REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lessons
(
    id         bigserial NOT NULL,
    room       int NOT NULL,
    weekday    varchar(50) NOT NULL,
    start_time time NOT NULL,
    end_time   time NOT NULL,
    subject_id bigint NOT NULL,
    group_id   bigint NOT NULL,
    teacher_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_subject FOREIGN KEY (subject_id) REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_teacher FOREIGN KEY (teacher_id) REFERENCES teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS marks
(
    id         bigserial NOT NULL,
    date       date NOT NULL,
    value      int NOT NULL,
    student_id bigint NOT NULL,
    teacher_id bigint NOT NULL,
    subject_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_teacher FOREIGN KEY (teacher_id) REFERENCES teachers_info (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_subject FOREIGN KEY (subject_id) REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE
);