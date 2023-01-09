package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.*;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MarkRowMapper {
    @SneakyThrows
    public static Optional<Mark> mapMark(ResultSet rs){
        Mark mark = new Mark();
        while (rs.next()) {
            mark.setId(rs.getLong(1));
            mark.setDate(rs.getDate(2).toLocalDate());
            mark.setValue(rs.getInt(3));

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setId(rs.getLong(4));
            studentInfo.setAdmissionYear(rs.getInt(5));
            studentInfo.setFaculty(rs.getString(6));
            studentInfo.setSpeciality(rs.getString(7));
            User student = new User();
            student.setId(rs.getLong(8));
            student.setName(rs.getString(9));
            student.setSurname(rs.getString(10));
            student.setEmail(rs.getString(11));
            student.setPassword(rs.getString(12));
            student.setRole(Role.valueOf(rs.getString(13).toUpperCase()));
            studentInfo.setUser(student);

            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setId(rs.getLong(14));
            User teacher = new User();
            teacher.setId(rs.getLong(15));
            teacher.setName(rs.getString(16));
            teacher.setSurname(rs.getString(17));
            teacher.setEmail(rs.getString(18));
            teacher.setPassword(rs.getString(19));
            teacher.setRole(Role.valueOf(rs.getString(20).toUpperCase()));
            teacherInfo.setUser(teacher);
            mark.setTeacher(teacherInfo);

            Group group = new Group();
            group.setId(rs.getLong(21));
            group.setNumber(rs.getInt(22));
            studentInfo.setGroup(group);
            mark.setStudent(studentInfo);

            Subject subject = new Subject();
            subject.setId(rs.getLong(23));
            subject.setName(rs.getString(24));
            mark.setSubject(subject);
        }
        return Optional.of(mark);
    }

    @SneakyThrows
    public static List<Mark> mapMarks(ResultSet rs){
        List<Mark> marks = new ArrayList<>();
        while (rs.next()) {
            Mark mark = new Mark();
            mark.setId(rs.getLong(1));
            mark.setDate(rs.getDate(2).toLocalDate());
            mark.setValue(rs.getInt(3));

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setId(rs.getLong(4));
            studentInfo.setAdmissionYear(rs.getInt(5));
            studentInfo.setFaculty(rs.getString(6));
            studentInfo.setSpeciality(rs.getString(7));
            User student = new User();
            student.setId(rs.getLong(8));
            student.setName(rs.getString(9));
            student.setSurname(rs.getString(10));
            student.setEmail(rs.getString(11));
            student.setPassword(rs.getString(12));
            student.setRole(Role.valueOf(rs.getString(13).toUpperCase()));
            studentInfo.setUser(student);

            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setId(rs.getLong(14));
            User teacher = new User();
            teacher.setId(rs.getLong(15));
            teacher.setName(rs.getString(16));
            teacher.setSurname(rs.getString(17));
            teacher.setEmail(rs.getString(18));
            teacher.setPassword(rs.getString(19));
            teacher.setRole(Role.valueOf(rs.getString(20).toUpperCase()));
            teacherInfo.setUser(teacher);
            mark.setTeacher(teacherInfo);

            Group group = new Group();
            group.setId(rs.getLong(21));
            group.setNumber(rs.getInt(22));
            studentInfo.setGroup(group);
            mark.setStudent(studentInfo);

            Subject subject = new Subject();
            subject.setId(rs.getLong(23));
            subject.setName(rs.getString(24));
            mark.setSubject(subject);

            marks.add(mark);
        }
        return marks;
    }

    @SneakyThrows
    public static List<Mark> mapMarksBySubjectAndTeacher(ResultSet rs){
        List<Mark> marks = new ArrayList<>();
        while (rs.next()) {
            Mark mark = new Mark();
            mark.setId(rs.getLong(1));
            mark.setDate(rs.getDate(2).toLocalDate());
            mark.setValue(rs.getInt(3));

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setId(rs.getLong(4));
            studentInfo.setAdmissionYear(rs.getInt(5));
            studentInfo.setFaculty(rs.getString(6));
            studentInfo.setSpeciality(rs.getString(7));

            User user = new User();
            user.setId(rs.getLong(8));
            user.setName(rs.getString(9));
            user.setSurname(rs.getString(10));
            user.setEmail(rs.getString(11));
            user.setPassword(rs.getString(12));
            user.setRole(Role.valueOf(rs.getString(13).toUpperCase()));
            studentInfo.setUser(user);

            Group group = new Group();
            group.setId(rs.getLong(14));
            group.setNumber(rs.getInt(15));
            studentInfo.setGroup(group);
            mark.setStudent(studentInfo);

            Subject subject = new Subject();
            subject.setId(rs.getLong(16));
            subject.setName(rs.getString(17));
            mark.setSubject(subject);

            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setId(rs.getLong(18));
            mark.setTeacher(teacherInfo);

            marks.add(mark);
        }
        return marks;
    }

    @SneakyThrows
    public static List<Mark> mapMarksByStudent(ResultSet rs){
        List<Mark> marks = new ArrayList<>();
        while (rs.next()) {
            Mark mark = new Mark();
            mark.setId(rs.getLong(1));
            mark.setDate(rs.getDate(2).toLocalDate());
            mark.setValue(rs.getInt(3));

            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setId(rs.getLong(4));

            User user = new User();
            user.setId(rs.getLong(5));
            user.setName(rs.getString(6));
            user.setSurname(rs.getString(7));
            user.setEmail(rs.getString(8));
            user.setPassword(rs.getString(9));
            user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
            teacherInfo.setUser(user);
            mark.setTeacher(teacherInfo);

            Subject subject = new Subject();
            subject.setId(rs.getLong(11));
            subject.setName(rs.getString(12));
            mark.setSubject(subject);

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setId(rs.getLong(13));
            mark.setStudent(studentInfo);

            marks.add(mark);
        }
        return marks;
    }
}
