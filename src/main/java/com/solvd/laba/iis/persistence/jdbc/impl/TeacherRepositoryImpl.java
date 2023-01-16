package com.solvd.laba.iis.persistence.jdbc.impl;

import com.solvd.laba.iis.domain.TeacherInfo;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.TeacherRepository;
import com.solvd.laba.iis.persistence.jdbc.mapper.TeacherRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "application", name = "repository", havingValue = "jdbc")
public class TeacherRepositoryImpl implements TeacherRepository {

    private static final String FIND_ALL_QUERY = """
            SELECT teachers_info.id as teacher_id,
            users_info.id as user_id, users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            subjects.id as subject_id, subjects.name as subject_name
            FROM teachers_info
            LEFT JOIN users_info ON (teachers_info.user_id = users_info.id)
            LEFT JOIN teachers_subjects ON (teachers_info.id = teachers_subjects.teacher_id)
            LEFT JOIN subjects ON (teachers_subjects.subject_id = subjects.id) """;
    private static final String CREATE_QUERY = "INSERT INTO teachers_info (user_id) VALUES(?)";
    private static final String DELETE_QUERY = "DELETE FROM teachers_info WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE teachers_info SET user_id = ? WHERE id = ?";
    private static final String ADD_SUBJECT_QUERY = "INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES(?,?)";
    private static final String DELETE_SUBJECT_QUERY = "DELETE FROM teachers_subjects WHERE teacher_id = ? AND subject_id = ?";

    private final DataSource dataSource;

    @Override
    public List<TeacherInfo> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return TeacherRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all teachers", ex);
        }
    }

    @Override
    public Optional<TeacherInfo> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE teachers_info.id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(TeacherRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding teacher by teacher's id = " + id, ex);
        }
    }

    @Override
    public List<TeacherInfo> findByGroup(Long groupId) {
        String joinQuery = "LEFT JOIN lessons ON (teachers_info.id = lessons.teacher_id) ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + joinQuery + "WHERE lessons.group_id = ?")) {
            statement.setLong(1, groupId);
            try (ResultSet rs = statement.executeQuery()) {
                return TeacherRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding teachers by group's id = " + groupId, ex);
        }
    }

    @Override
    public List<TeacherInfo> findBySubject(Long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE subjects.id = ?")) {
            statement.setLong(1, subjectId);
            try (ResultSet rs = statement.executeQuery()) {
                return TeacherRowMapper.mapRowsBySubject(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding teachers by subject's id = " + subjectId, ex);
        }
    }

    @Override
    public void create(TeacherInfo teacherInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, teacherInfo.getUser().getId());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    teacherInfo.setId(key.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating teacher", ex);
        }
    }

    @Override
    public void update(TeacherInfo teacherInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setLong(1, teacherInfo.getUser().getId());
            statement.setLong(2, teacherInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving teacher with id = " + teacherInfo.getId(), ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting teacher with id = " + id, ex);
        }
    }

    @Override
    public void deleteSubject(Long teacherId, Long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SUBJECT_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setLong(2, subjectId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting subject with id = " + subjectId, ex);
        }
    }

    @Override
    public void addSubject(Long teacherId, Long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_SUBJECT_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setLong(2, subjectId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while adding subject with id = " + subjectId, ex);
        }
    }

}
