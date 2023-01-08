package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.*;
import com.solvd.laba.iis.domain.exception.DaoException;
import com.solvd.laba.iis.persistence.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonRepository {
    private final DataSource dataSource;
    private static final String FIND_ALL_QUERY = """
            SELECT lessons.id, lessons.room, lessons.weekday, lessons.start_time, lessons.end_time,
            subjects.id,  subjects.name,
            groups.id, groups.number,
            teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)""";
    private static final String FIND_BY_ID_QUERY = """
            SELECT lessons.id, lessons.room, lessons.weekday, lessons.start_time, lessons.end_time,
            subjects.id,  subjects.name,
            groups.id, groups.number,
            teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE lessons.id = ?""";
    private static final String FIND_BY_GROUP_QUERY = """
            SELECT lessons.id, lessons.room, lessons.weekday, lessons.start_time, lessons.end_time,
            subjects.id,  subjects.name,
            groups.id, groups.number,
            teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE groups.id = ?""";
    private static final String FIND_BY_GROUP_AND_DAY_QUERY = """
            SELECT lessons.id, lessons.room, lessons.weekday, lessons.start_time, lessons.end_time,
            subjects.id,  subjects.name,
            groups.id, groups.number,
            teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE groups.id = ? AND lessons.weekday = ?""";

    private static final String FIND_BY_TEACHER_QUERY = """
            SELECT lessons.id, lessons.room, lessons.weekday, lessons.start_time, lessons.end_time,
            subjects.id,  subjects.name,
            groups.id, groups.number,
            teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE teachers_info.id = ?""";

    private static final String FIND_BY_TEACHER_AND_DAY_QUERY = """
            SELECT lessons.id, lessons.room, lessons.weekday, lessons.start_time, lessons.end_time,
            subjects.id,  subjects.name,
            groups.id, groups.number,
            teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.subjects ON (lessons.subject_id = subjects.id)
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            LEFT JOIN iis_schema.teachers_info ON (lessons.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE teachers_info.id = ? AND lessons.weekday = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.lessons (room, weekday, start_time, end_time, subject_id, group_id, teacher_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.lessons WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.lessons SET room = ?, weekday = ?, start_time = ?, end_time = ?, subject_id = ?, group_id = ?, teacher_id = ? WHERE id = ?";

    @Override
    public List<Lesson> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            List<Lesson> lessons = new ArrayList<>();
            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding all lessons");
        }
    }

    @Override
    public Optional<Lesson> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding lesson by lesson's id = " + id);
        }
    }

    @Override
    public List<Lesson> findByGroup(long groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_QUERY)) {
            statement.setLong(1, groupId);
            ResultSet rs = statement.executeQuery();
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding lessons by group's id = " + groupId);
        }
    }

    @Override
    public List<Lesson> findByGroupAndDay(long groupId, String weekday) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_AND_DAY_QUERY)) {
            statement.setLong(1, groupId);
            statement.setString(2, weekday);
            ResultSet rs = statement.executeQuery();
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding lessons by group's id = " + groupId + " and day = " + weekday);
        }
    }

    @Override
    public List<Lesson> findByTeacher(long teacherId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TEACHER_QUERY)) {
            statement.setLong(1, teacherId);
            ResultSet rs = statement.executeQuery();
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding lessons by teacher's id = " + teacherId);
        }
    }

    @Override
    public List<Lesson> findByTeacherAndDay(long teacherId, String weekday) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TEACHER_AND_DAY_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setString(2, weekday);
            ResultSet rs = statement.executeQuery();
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding lessons by teacher's id = " + teacherId + " and day = " + weekday);
        }
    }

    @Override
    public Lesson create(Lesson lesson) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, lesson.getRoom());
            statement.setString(2, lesson.getWeekday().toString());
            statement.setTime(3, Time.valueOf(lesson.getStartTime()));
            statement.setTime(4, Time.valueOf(lesson.getEndTime()));
            statement.setLong(5, lesson.getSubject().getId());
            statement.setLong(6, lesson.getGroup().getId());
            statement.setLong(7, lesson.getTeacher().getId());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            if (key.next()) {
                lesson.setId(key.getLong(1));
            }
            return lesson;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while creating lesson");
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
            throw new DaoException("Exception occurred while saving lesson with id = " + lesson.getId());
        }
    }

    @Override
    public void delete(Lesson lesson) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, lesson.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while deleting lesson with id = " + lesson.getId());
        }
    }
}
