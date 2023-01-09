package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.*;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonRowMapper {
    @SneakyThrows
    public static Optional<Lesson> mapLesson(ResultSet rs){
        Lesson lesson = new Lesson();
        while (rs.next()) {
            lesson.setId(rs.getLong(1));
            lesson.setRoom(rs.getInt(2));
            lesson.setWeekday(Weekday.valueOf(rs.getString(3).toUpperCase()));
            lesson.setStartTime(rs.getTime(4).toLocalTime());
            lesson.setEndTime(rs.getTime(5).toLocalTime());

            Subject subject = new Subject();
            subject.setId(rs.getLong(6));
            subject.setName(rs.getString(7));
            lesson.setSubject(subject);

            Group group = new Group();
            group.setId(rs.getLong(8));
            group.setNumber(rs.getInt(9));
            lesson.setGroup(group);

            TeacherInfo teacher = new TeacherInfo();
            teacher.setId(rs.getLong(10));

            User user = new User();
            user.setId(rs.getLong(11));
            user.setName(rs.getString(12));
            user.setSurname(rs.getString(13));
            user.setEmail(rs.getString(14));
            user.setPassword(rs.getString(15));
            user.setRole(Role.valueOf(rs.getString(16).toUpperCase()));
            teacher.setUser(user);
            lesson.setTeacher(teacher);
        }
        return Optional.of(lesson);
    }

    @SneakyThrows
    public static List<Lesson> mapLessons(ResultSet rs){
        List<Lesson> lessons = new ArrayList<>();
        while (rs.next()) {
            Lesson lesson = new Lesson();
            lesson.setId(rs.getLong(1));
            lesson.setRoom(rs.getInt(2));
            lesson.setWeekday(Weekday.valueOf(rs.getString(3).toUpperCase()));
            lesson.setStartTime(rs.getTime(4).toLocalTime());
            lesson.setEndTime(rs.getTime(5).toLocalTime());

            Subject subject = new Subject();
            subject.setId(rs.getLong(6));
            subject.setName(rs.getString(7));
            lesson.setSubject(subject);

            Group group = new Group();
            group.setId(rs.getLong(8));
            group.setNumber(rs.getInt(9));
            lesson.setGroup(group);

            TeacherInfo teacher = new TeacherInfo();
            teacher.setId(rs.getLong(10));

            User user = new User();
            user.setId(rs.getLong(11));
            user.setName(rs.getString(12));
            user.setSurname(rs.getString(13));
            user.setEmail(rs.getString(14));
            user.setPassword(rs.getString(15));
            user.setRole(Role.valueOf(rs.getString(16).toUpperCase()));
            teacher.setUser(user);
            lesson.setTeacher(teacher);

            lessons.add(lesson);
        }
        return lessons;
    }
}
