package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.*;
import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.Lesson;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class LessonRowMapper {

    @SneakyThrows
    public static Lesson mapRow(ResultSet rs) {
        Lesson lesson = new Lesson();
        lesson.setId(rs.getLong("lesson_id"));
        lesson.setRoom(rs.getInt("lesson_room"));
        lesson.setWeekday(Lesson.Weekday.valueOf(rs.getString("lesson_weekday").toUpperCase()));
        lesson.setStartTime(rs.getTime("start_time").toLocalTime());
        lesson.setEndTime(rs.getTime("end_time").toLocalTime());

        Subject subject = SubjectRowMapper.mapRow(rs);
        lesson.setSubject(subject);

        Group group = GroupRowMapper.mapRow(rs);
        lesson.setGroup(group);

        TeacherInfo teacher = new TeacherInfo();
        teacher.setId(rs.getLong("teacher_id"));
        UserInfo userInfo = UserRowMapper.mapRow(rs);
        teacher.setUser(userInfo);

        lesson.setTeacher(teacher);
        return lesson;
    }

    @SneakyThrows
    public static List<Lesson> mapRows(ResultSet rs) {
        List<Lesson> lessons = new ArrayList<>();
        while (rs.next()) {
            Lesson lesson = mapRow(rs);
            lessons.add(lesson);
        }
        return lessons;
    }

}
