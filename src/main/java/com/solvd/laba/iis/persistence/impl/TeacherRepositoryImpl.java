package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.Role;
import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.domain.exception.DaoException;
import com.solvd.laba.iis.persistence.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeacherRepositoryImpl implements TeacherRepository {
    private final DataSource dataSource;
    private static final String FIND_ALL_QUERY = """
            SELECT teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role,
            subjects.id, subjects.name
            FROM iis_schema.teachers_info
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            LEFT JOIN iis_schema.teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            LEFT JOIN iis_schema.subjects ON (teachers_subjects.subject_id = subjects.id);""";
    private static final String FIND_BY_ID_QUERY = """
            SELECT teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role,
            subjects.id, subjects.name
            FROM iis_schema.teachers_info
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            LEFT JOIN iis_schema.teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            LEFT JOIN iis_schema.subjects ON (teachers_subjects.subject_id = subjects.id)
            WHERE teachers_info.id = ?;""";
    private static final String FIND_BY_GROUP_QUERY = """
            SELECT teachers_info.id,
            users.id, users.name, users.surname, users.email, users.password, users.role,
            subjects.id, subjects.name
            FROM iis_schema.teachers_info
            INNER JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            INNER JOIN iis_schema.teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            INNER JOIN iis_schema.subjects ON (teachers_subjects.subject_id = subjects.id)
            INNER JOIN iis_schema.lessons ON (teachers_info.id = lessons.teacher_id)
            WHERE lessons.group_id = ?;""";
    private static final String FIND_BY_SUBJECT_QUERY = """
            SELECT teachers_info.id
            FROM iis_schema.teachers_info
            INNER JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            INNER JOIN iis_schema.teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            INNER JOIN iis_schema.subjects ON (teachers_subjects.subject_id = subjects.id)
            WHERE subjects.id = ?;""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.teachers_info (user_id) VALUES(?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.teachers_info WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.teachers_info SET user_id = ? WHERE id = ?";
    private static final String ADD_SUBJECT_QUERY = "INSERT INTO iis_schema.teachers_subjects (teacher_id, subject_id) VALUES(?,?)";
    private static final String DELETE_SUBJECT_QUERY = "DELETE FROM iis_schema.teachers_subjects WHERE teacher_id = ? AND subject_id = ?";

    @Override
    public List<TeacherInfo> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            List<TeacherInfo> teachers = new ArrayList<>();
            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding all teachers");
        }
    }

    @Override
    public Optional<TeacherInfo> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding teacher by teacher's id = " + id);
        }
    }

    @Override
    public List<TeacherInfo> findByGroup(long groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_QUERY)) {
            statement.setLong(1, groupId);
            ResultSet rs = statement.executeQuery();
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
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding teachers by group's id = " + groupId);
        }
    }

    @Override
    public List<TeacherInfo> findBySubject(long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SUBJECT_QUERY)) {
            statement.setLong(1, subjectId);
            ResultSet rs = statement.executeQuery();
            List<TeacherInfo> teachers = new ArrayList<>();
            while (rs.next()) {
                findById(rs.getLong(1)).ifPresent(teachers::add);
            }
            return teachers;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding teachers by subject's id = " + subjectId);
        }
    }

    @Override
    @Transactional
    public TeacherInfo create(TeacherInfo teacherInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setLong(1, teacherInfo.getUser().getId());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            if(key.next()){
                teacherInfo.setId(key.getLong(1));
            }
            return teacherInfo;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while creating teacher");
        }
    }

    @Override
    @Transactional
    public TeacherInfo save(TeacherInfo teacherInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setLong(1, teacherInfo.getUser().getId());
            statement.setLong(2, teacherInfo.getId());
            statement.executeUpdate();
            return teacherInfo;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while saving teacher with id = " + teacherInfo.getId());
        }
    }

    @Override
    @Transactional
    public void delete(TeacherInfo teacherInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, teacherInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while deleting teacher with id = " + teacherInfo.getId());
        }
    }

    @Override
    @Transactional
    public void deleteSubject(long teacherId, long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SUBJECT_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setLong(2, subjectId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while deleting subject with id = " + subjectId);
        }
    }

    @Override
    @Transactional
    public void addSubject(long teacherId, long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_SUBJECT_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setLong(2, subjectId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while adding subject with id = " + subjectId);
        }
    }
}
