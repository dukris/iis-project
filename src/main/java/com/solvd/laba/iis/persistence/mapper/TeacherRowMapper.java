package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Role;
import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TeacherRowMapper {
    @SneakyThrows
    public static Optional<TeacherInfo> mapTeacher(ResultSet rs){
        TeacherInfo teacher = new TeacherInfo();
        List<Subject> subjects = new ArrayList<>();
        boolean isDuplicated = false;
        while (rs.next()) {
            if (!isDuplicated) {
                teacher.setId(rs.getLong(1));
                User user = new User();
                user.setId(rs.getLong(2));
                user.setName(rs.getString(3));
                user.setSurname(rs.getString(4));
                user.setEmail(rs.getString(5));
                user.setPassword(rs.getString(6));
                user.setRole(Role.valueOf(rs.getString(7).toUpperCase()));
                teacher.setUser(user);
                isDuplicated = true;
            }
            Subject subject = new Subject();
            subject.setId(rs.getLong(8));
            subject.setName(rs.getString(9));
            subjects.add(subject);
        }
        teacher.setSubjects(subjects);
        return Optional.of(teacher);
    }

    @SneakyThrows
    public static List<TeacherInfo> mapTeachers(ResultSet rs){
        List<TeacherInfo> teachers = new ArrayList<>();
        long prevId = 0;
        long currentId;
        TeacherInfo prevTeacher = null;
        while (rs.next()) {
            currentId = rs.getLong(1);
            if (prevId == currentId && Objects.nonNull(prevTeacher)) {
                List<Subject> subjectList = prevTeacher.getSubjects();
                Subject subject = new Subject();
                subject.setId(rs.getLong(8));
                subject.setName(rs.getString(9));
                subjectList.add(subject);
                prevTeacher.setSubjects(subjectList);
            } else {
                if (Objects.nonNull(prevTeacher)) {
                    teachers.add(prevTeacher);
                }
                TeacherInfo teacher = new TeacherInfo();
                teacher.setId(currentId);

                User user = new User();
                user.setId(rs.getLong(2));
                user.setName(rs.getString(3));
                user.setSurname(rs.getString(4));
                user.setEmail(rs.getString(5));
                user.setPassword(rs.getString(6));
                user.setRole(Role.valueOf(rs.getString(7).toUpperCase()));

                teacher.setUser(user);

                Subject subject = new Subject();
                subject.setId(rs.getLong(8));
                subject.setName(rs.getString(9));

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
    public static List<TeacherInfo> mapTeachersBySubject(ResultSet rs){
        List<TeacherInfo> teachers = new ArrayList<>();
        while (rs.next()) {
            TeacherInfo teacher = new TeacherInfo();
            teacher.setId(rs.getLong(1));
            User user = new User();
            user.setId(rs.getLong(2));
            user.setName(rs.getString(3));
            user.setSurname(rs.getString(4));
            user.setEmail(rs.getString(5));
            user.setPassword(rs.getString(6));
            user.setRole(Role.valueOf(rs.getString(7).toUpperCase()));
            teacher.setUser(user);
            teachers.add(teacher);
        }
        return teachers;
    }
}
