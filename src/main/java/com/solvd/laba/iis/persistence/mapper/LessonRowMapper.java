package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.*;
import com.solvd.laba.iis.web.mapper.SubjectMapper;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class LessonRowMapper {
    @SneakyThrows
    public static Optional<Lesson> mapLesson(ResultSet rs) {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getLong("lesson_id"));
        lesson.setRoom(rs.getInt("lesson_room"));
        lesson.setWeekday(Weekday.valueOf(rs.getString("lesson_weekday").toUpperCase()));
        lesson.setStartTime(rs.getTime("start_time").toLocalTime());
        lesson.setEndTime(rs.getTime("end_time").toLocalTime());

        Subject subject = SubjectRowMapper.mapSubject(rs).orElseThrow();
        lesson.setSubject(subject);

        Group group = GroupRowMapper.mapGroup(rs).orElseThrow();
        lesson.setGroup(group);

        TeacherInfo teacher = new TeacherInfo();
        teacher.setId(rs.getLong("teacher_id"));
        User user = UserRowMapper.mapUser(rs).orElseThrow();
        teacher.setUser(user);

        lesson.setTeacher(teacher);
        return Optional.of(lesson);
    }

    @SneakyThrows
    public static List<Lesson> mapLessons(ResultSet rs) {
        List<Lesson> lessons = new ArrayList<>();
        while (rs.next()) {
            Lesson lesson = mapLesson(rs).orElseThrow();
            lessons.add(lesson);
        }
        return lessons;
    }
}
