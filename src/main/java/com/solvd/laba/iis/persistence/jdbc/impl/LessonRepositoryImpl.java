package com.solvd.laba.iis.persistence.jdbc.impl;

import com.solvd.laba.iis.domain.Lesson;
import com.solvd.laba.iis.domain.criteria.LessonSearchCriteria;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.LessonRepository;
import com.solvd.laba.iis.persistence.jdbc.mapper.LessonRowMapper;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonRepository {

    private static final String FIND_ALL_QUERY = """
            SELECT lessons.id as lesson_id, lessons.room as lesson_room, lessons.weekday as lesson_weekday, lessons.start_time as start_time, lessons.end_time as end_time,
            subjects.id as subject_id,  subjects.name as subject_name,
            groups.id as group_id, groups.number as group_number,
            teachers_info.id as teacher_id,
            users_info.id as user_id, users_info.name as user_name, users_info.surname as user_surname, users_info.email as user_email, users_info.password as user_password, users_info.role as user_role
            FROM lessons
            LEFT JOIN subjects ON lessons.subject_id = subjects.id
            LEFT JOIN groups ON lessons.group_id = groups.id
            LEFT JOIN teachers_info ON lessons.teacher_id = teachers_info.id
            LEFT JOIN users_info ON teachers_info.user_id = users_info.id""";
    private static final String CREATE_QUERY = "INSERT INTO lessons (room, weekday, start_time, end_time, subject_id, group_id, teacher_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM lessons WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE lessons SET room = ?, weekday = ?, start_time = ?, end_time = ?, subject_id = ?, group_id = ?, teacher_id = ? WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public List<Lesson> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return LessonRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all lessons", ex);
        }
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + " WHERE lessons.id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(LessonRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding lesson by lesson's id = " + id, ex);
        }
    }

    @Override
    public List<Lesson> findByStudentCriteria(Long groupId, LessonSearchCriteria lessonSearchCriteria) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(updateStudentQuery(groupId, lessonSearchCriteria))) {
                return LessonRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding lessons by group's id = " + groupId, ex);
        }
    }

    @Override
    public List<Lesson> findByTeacherCriteria(Long teacherId, LessonSearchCriteria lessonSearchCriteria) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(updateTeacherQuery(teacherId, lessonSearchCriteria))) {
                return LessonRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding lessons by teacher's id = " + teacherId, ex);
        }
    }

    private String updateStudentQuery(Long groupId, LessonSearchCriteria lessonSearchCriteria) {
        return Objects.nonNull(lessonSearchCriteria.getWeekday()) ?
                FIND_ALL_QUERY + " WHERE groups.id = " + groupId + " AND lessons.weekday = \'" + lessonSearchCriteria.getWeekday().toUpperCase() + "\'" :
                FIND_ALL_QUERY + " WHERE groups.id = " + groupId;
    }

    private String updateTeacherQuery(Long teacherId, LessonSearchCriteria lessonSearchCriteria) {
        return Objects.nonNull(lessonSearchCriteria.getWeekday()) ?
                FIND_ALL_QUERY + " WHERE teachers_info.id = " + teacherId + " AND lessons.weekday = \'" + lessonSearchCriteria.getWeekday().toUpperCase() + "\'" :
                FIND_ALL_QUERY + " WHERE teachers_info.id = " + teacherId;
    }

    @Override
    public void create(Lesson lesson) {
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
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating lesson", ex);
        }
    }

    @Override
    public void update(Lesson lesson) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, lesson.getRoom());
            statement.setString(2, lesson.getWeekday().toString());
            statement.setTime(3, Time.valueOf(lesson.getStartTime()));
            statement.setTime(4, Time.valueOf(lesson.getEndTime()));
            statement.setLong(5, lesson.getSubject().getId());
            statement.setLong(6, lesson.getGroup().getId());
            statement.setLong(7, lesson.getTeacher().getId());
            statement.setLong(8, lesson.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving lesson with id = " + lesson.getId(), ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting lesson with id = " + id, ex);
        }
    }

}
