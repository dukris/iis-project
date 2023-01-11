package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.TeacherRepository;
import com.solvd.laba.iis.persistence.mapper.TeacherRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeacherRepositoryImpl implements TeacherRepository {

    private static final String FIND_ALL_QUERY = """
            SELECT teachers_info.id as teacher_id,
            users_info.id as user_id, users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            subjects.id as subject_id, subjects.name as subject_name
            FROM iis_schema.teachers_info
            LEFT JOIN iis_schema.users_info ON (teachers_info.user_id = users_info.id)
            LEFT JOIN iis_schema.teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            LEFT JOIN iis_schema.subjects ON (teachers_subjects.subject_id = subjects.id)""";
    private static final String FIND_BY_ID_QUERY = """
            SELECT teachers_info.id as teacher_id,
            users_info.id as user_id, users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            subjects.id as subject_id, subjects.name as subject_name
            FROM iis_schema.teachers_info
            LEFT JOIN iis_schema.users_info ON (teachers_info.user_id = users_info.id)
            LEFT JOIN iis_schema.teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            LEFT JOIN iis_schema.subjects ON (teachers_subjects.subject_id = subjects.id)
            WHERE teachers_info.id = ?""";
    private static final String FIND_BY_GROUP_QUERY = """
            SELECT teachers_info.id as teacher_id,
            users_info.id as user_id, users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            subjects.id as subject_id, subjects.name as subject_name
            FROM iis_schema.teachers_info
            LEFT JOIN iis_schema.users_info ON (teachers_info.user_id = users_info.id)
            LEFT JOIN iis_schema.teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            LEFT JOIN iis_schema.subjects ON (teachers_subjects.subject_id = subjects.id)
            LEFT JOIN iis_schema.lessons ON (teachers_info.id = lessons.teacher_id)
            WHERE lessons.group_id = ?""";
    private static final String FIND_BY_SUBJECT_QUERY = """
            SELECT teachers_info.id as teacher_id,
            users_info.id as user_id, users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role
            FROM iis_schema.teachers_info
            LEFT JOIN iis_schema.users_info ON (teachers_info.user_id = users_info.id)
            LEFT JOIN iis_schema.teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            LEFT JOIN iis_schema.subjects ON (teachers_subjects.subject_id = subjects.id)
            WHERE subjects.id = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.teachers_info (user_id) VALUES(?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.teachers_info WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.teachers_info SET user_id = ? WHERE id = ?";
    private static final String ADD_SUBJECT_QUERY = "INSERT INTO iis_schema.teachers_subjects (teacher_id, subject_id) VALUES(?,?)";
    private static final String DELETE_SUBJECT_QUERY = "DELETE FROM iis_schema.teachers_subjects WHERE teacher_id = ? AND subject_id = ?";
    private final DataSource dataSource;

    @Override
    public List<TeacherInfo> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return TeacherRowMapper.mapTeachers(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all teachers");
        }
    }

    @Override
    public Optional<TeacherInfo> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(TeacherRowMapper.mapTeacher(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding teacher by teacher's id = " + id);
        }
    }

    @Override
    public List<TeacherInfo> findByGroup(long groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_QUERY)) {
            statement.setLong(1, groupId);
            try (ResultSet rs = statement.executeQuery()) {
                return TeacherRowMapper.mapTeachers(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding teachers by group's id = " + groupId);
        }
    }

    @Override
    public List<TeacherInfo> findBySubject(long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SUBJECT_QUERY)) {
            statement.setLong(1, subjectId);
            try (ResultSet rs = statement.executeQuery()) {
                return TeacherRowMapper.mapTeachersBySubject(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding teachers by subject's id = " + subjectId);
        }
    }

    @Override
    public void create(TeacherInfo teacherInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, teacherInfo.getUserInfo().getId());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    teacherInfo.setId(key.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating teacher");
        }
    }

    @Override
    public void save(TeacherInfo teacherInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setLong(1, teacherInfo.getUserInfo().getId());
            statement.setLong(2, teacherInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving teacher with id = " + teacherInfo.getId());
        }
    }

    @Override
    public void delete(TeacherInfo teacherInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, teacherInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting teacher with id = " + teacherInfo.getId());
        }
    }

    @Override
    public void deleteSubject(long teacherId, long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SUBJECT_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setLong(2, subjectId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting subject with id = " + subjectId);
        }
    }

    @Override
    public void addSubject(long teacherId, long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_SUBJECT_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setLong(2, subjectId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while adding subject with id = " + subjectId);
        }
    }

}
