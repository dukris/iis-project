package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class TeacherRowMapper {
    @SneakyThrows
    public static Optional<TeacherInfo> mapTeacher(ResultSet rs) {
        TeacherInfo teacher = new TeacherInfo();
        List<Subject> subjects = new ArrayList<>();

        teacher.setId(rs.getLong("teacher_id"));
        User user = UserRowMapper.mapUser(rs).orElseThrow();
        teacher.setUser(user);

        Subject subject = SubjectRowMapper.mapSubject(rs).orElseThrow();
        subjects.add(subject);
        rs.next();
        subject = SubjectRowMapper.mapSubject(rs).orElseThrow();
        subjects.add(subject);
        teacher.setSubjects(subjects);
        return Optional.of(teacher);
    }

    @SneakyThrows
    public static List<TeacherInfo> mapTeachers(ResultSet rs) {
        List<TeacherInfo> teachers = new ArrayList<>();
        long prevId = 0;
        long currentId;
        TeacherInfo prevTeacher = null;
        while (rs.next()) {
            currentId = rs.getLong("teacher_id");
            if (prevId == currentId && Objects.nonNull(prevTeacher)) {
                List<Subject> subjectList = prevTeacher.getSubjects();
                Subject subject = SubjectRowMapper.mapSubject(rs).orElseThrow();
                subjectList.add(subject);
                prevTeacher.setSubjects(subjectList);
            } else {
                if (Objects.nonNull(prevTeacher)) {
                    teachers.add(prevTeacher);
                }
                TeacherInfo teacher = new TeacherInfo();
                teacher.setId(currentId);
                User user = UserRowMapper.mapUser(rs).orElseThrow();
                teacher.setUser(user);

                Subject subject = SubjectRowMapper.mapSubject(rs).orElseThrow();
                List<Subject> subjects = new ArrayList<>();
                subjects.add(subject);
                teacher.setSubjects(subjects);
                prevTeacher = teacher;
                prevId = currentId;
            }
        }
        teachers.add(prevTeacher);
        return teachers;
    }

    @SneakyThrows
    public static List<TeacherInfo> mapTeachersBySubject(ResultSet rs) {
        List<TeacherInfo> teachers = new ArrayList<>();
        while(rs.next()){
            TeacherInfo teacher = new TeacherInfo();
            teacher.setId(rs.getLong("teacher_id"));
            User user = UserRowMapper.mapUser(rs).orElseThrow();
            teacher.setUser(user);
            teachers.add(teacher);
        }
        return teachers;
    }
}
