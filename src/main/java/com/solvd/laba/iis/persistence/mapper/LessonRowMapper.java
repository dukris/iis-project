package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.*;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class LessonRowMapper {

    @SneakyThrows
    public static Lesson mapLesson(ResultSet rs) {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getLong("lesson_id"));
        lesson.setRoom(rs.getInt("lesson_room"));
        lesson.setWeekday(Lesson.Weekday.valueOf(rs.getString("lesson_weekday").toUpperCase()));
        lesson.setStartTime(rs.getTime("start_time").toLocalTime());
        lesson.setEndTime(rs.getTime("end_time").toLocalTime());

        Subject subject = SubjectRowMapper.mapSubject(rs);
        lesson.setSubject(subject);

        Group group = GroupRowMapper.mapGroup(rs);
        lesson.setGroup(group);

        TeacherInfo teacher = new TeacherInfo();
        teacher.setId(rs.getLong("teacher_id"));
        UserInfo userInfo = UserRowMapper.mapUser(rs);
        teacher.setUser(userInfo);

        lesson.setTeacher(teacher);
        return lesson;
    }

    @SneakyThrows
    public static List<Lesson> mapLessons(ResultSet rs) {
        List<Lesson> lessons = new ArrayList<>();
        while (rs.next()) {
            Lesson lesson = mapLesson(rs);
            lessons.add(lesson);
        }
        return lessons;
    }

}
