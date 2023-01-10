package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.LessonRepository;
import com.solvd.laba.iis.persistence.mapper.LessonRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonRepository {

    private static final String FIND_ALL_QUERY = """
            SELECT lessons.id as lesson_id, lessons.room as lesson_room, lessons.weekday as lesson_weekday, lessons.start_time as start_time, lessons.end_time as end_time,
            subjects.id as subject_id,  subjects.name as subject_name,
            groups.id as group_id, groups.number as group_number,
            teachers_info.id as teacher_id,
            users.id as user_id, users.name as user_name, users.surname as user_surname, users.email as user_email, users.password as user_password, users.role as user_role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)""";
    private static final String FIND_BY_ID_QUERY = """
            SELECT lessons.id as lesson_id, lessons.room as lesson_room, lessons.weekday as lesson_weekday, lessons.start_time as start_time, lessons.end_time as end_time,
            subjects.id as subject_id,  subjects.name as subject_name,
            groups.id as group_id, groups.number as group_number,
            teachers_info.id as teacher_id,
            users.id as user_id, users.name as user_name, users.surname as user_surname, users.email as user_email, users.password as user_password, users.role as user_role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE lessons.id = ?""";
    private static final String FIND_BY_GROUP_QUERY = """
            SELECT lessons.id as lesson_id, lessons.room as lesson_room, lessons.weekday as lesson_weekday, lessons.start_time as start_time, lessons.end_time as end_time,
            subjects.id as subject_id,  subjects.name as subject_name,
            groups.id as group_id, groups.number as group_number,
            teachers_info.id as teacher_id,
            users.id as user_id, users.name as user_name, users.surname as user_surname, users.email as user_email, users.password as user_password, users.role as user_role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE groups.id = ?""";
    private static final String FIND_BY_GROUP_AND_DAY_QUERY = """
            SELECT lessons.id as lesson_id, lessons.room as lesson_room, lessons.weekday as lesson_weekday, lessons.start_time as start_time, lessons.end_time as end_time,
            subjects.id as subject_id,  subjects.name as subject_name,
            groups.id as group_id, groups.number as group_number,
            teachers_info.id as teacher_id,
            users.id as user_id, users.name as user_name, users.surname as user_surname, users.email as user_email, users.password as user_password, users.role as user_role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE groups.id = ? AND lessons.weekday = ?""";

    private static final String FIND_BY_TEACHER_QUERY = """
            SELECT lessons.id as lesson_id, lessons.room as lesson_room, lessons.weekday as lesson_weekday, lessons.start_time as start_time, lessons.end_time as end_time,
            subjects.id as subject_id,  subjects.name as subject_name,
            groups.id as group_id, groups.number as group_number,
            teachers_info.id as teacher_id,
            users.id as user_id, users.name as user_name, users.surname as user_surname, users.email as user_email, users.password as user_password, users.role as user_role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE teachers_info.id = ?""";

    private static final String FIND_BY_TEACHER_AND_DAY_QUERY = """
            SELECT lessons.id as lesson_id, lessons.room as lesson_room, lessons.weekday as lesson_weekday, lessons.start_time as start_time, lessons.end_time as end_time,
            subjects.id as subject_id,  subjects.name as subject_name,
            groups.id as group_id, groups.number as group_number,
            teachers_info.id as teacher_id,
            users.id as user_id, users.name as user_name, users.surname as user_surname, users.email as user_email, users.password as user_password, users.role as user_role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE teachers_info.id = ? AND lessons.weekday = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.lessons (room, weekday, start_time, end_time, subject_id, group_id, teacher_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.lessons WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.lessons SET room = ?, weekday = ?, start_time = ?, end_time = ?, subject_id = ?, group_id = ?, teacher_id = ? WHERE id = ?";
    private final DataSource dataSource;

    @Override
    public List<Lesson> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return LessonRowMapper.mapLessons(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all lessons");
        }
    }

    @Override
    public Optional<Lesson> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(LessonRowMapper.mapLesson(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding lesson by lesson's id = " + id);
        }
    }

    @Override
    public List<Lesson> findByGroup(long groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_QUERY)) {
            statement.setLong(1, groupId);
            try (ResultSet rs = statement.executeQuery()) {
                return LessonRowMapper.mapLessons(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding lessons by group's id = " + groupId);
        }
    }

    @Override
    public List<Lesson> findByGroupAndDay(long groupId, String weekday) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_AND_DAY_QUERY)) {
            statement.setLong(1, groupId);
            statement.setString(2, weekday);
            try (ResultSet rs = statement.executeQuery()) {
                return LessonRowMapper.mapLessons(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding lessons by group's id = " + groupId + " and day = " + weekday);
        }
    }

    @Override
    public List<Lesson> findByTeacher(long teacherId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TEACHER_QUERY)) {
            statement.setLong(1, teacherId);
            try (ResultSet rs = statement.executeQuery()) {
                return LessonRowMapper.mapLessons(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding lessons by teacher's id = " + teacherId);
        }
    }

    @Override
    public List<Lesson> findByTeacherAndDay(long teacherId, String weekday) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TEACHER_AND_DAY_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setString(2, weekday);
            try (ResultSet rs = statement.executeQuery()) {
                return LessonRowMapper.mapLessons(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding lessons by teacher's id = " + teacherId + " and day = " + weekday);
        }
    }

    @Override
    public Lesson create(Lesson lesson) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, lesson.getRoom());
            statement.setString(2, lesson.getWeekday().toString());
            statement.setTime(3, Time.valueOf(lesson.getStartTime()));
            statement.setTime(4, Time.valueOf(lesson.getEndTime()));
            statement.setLong(5, lesson.getSubject().getId());
            statement.setLong(6, lesson.getGroup().getId());
            statement.setLong(7, lesson.getTeacher().getId());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    lesson.setId(key.getLong(1));
                }
                return lesson;
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating lesson");
        }
    }

    @Override
    public Lesson save(Lesson lesson) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setInt(1, lesson.getRoom());
            statement.setString(2, lesson.getWeekday().toString());
            statement.setTime(3, Time.valueOf(lesson.getStartTime()));
            statement.setTime(4, Time.valueOf(lesson.getEndTime()));
            statement.setLong(5, lesson.getSubject().getId());
            statement.setLong(6, lesson.getGroup().getId());
            statement.setLong(7, lesson.getTeacher().getId());
            statement.setLong(8, lesson.getId());
            statement.executeUpdate();
            return lesson;
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving lesson with id = " + lesson.getId());
        }
    }

    @Override
    public void delete(Lesson lesson) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, lesson.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting lesson with id = " + lesson.getId());
        }
    }
}
