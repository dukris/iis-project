package com.solvd.laba.iis.persistence.jdbc.impl;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.StudentRepository;
import com.solvd.laba.iis.persistence.jdbc.mapper.StudentRowMapper;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private static final String FIND_ALL_QUERY = """
            SELECT students_info.id as student_id, students_info.year as student_year, students_info.faculty as student_faculty, students_info.speciality as student_speciality,
            users_info.id as user_id,  users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            groups.id as group_id, groups.number as group_number
            FROM students_info
            LEFT JOIN users_info ON (students_info.user_id = users_info.id)
            LEFT JOIN groups ON (students_info.group_id = groups.id) """;
    private static final String CREATE_QUERY = "INSERT INTO students_info (year, faculty, speciality, user_id, group_id) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM students_info WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE students_info SET year = ?, faculty = ?, speciality = ?, user_id = ?, group_id = ? WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public List<StudentInfo> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return StudentRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all students", ex);
        }
    }

    @Override
    public Optional<StudentInfo> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE students_info.id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(StudentRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding student by id = " + id, ex);
        }
    }

    @Override
    public Optional<StudentInfo> findByUserId(Long userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE students_info.user_id = ?")) {
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(StudentRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding student by user's id = " + userId, ex);
        }
    }

    @Override
    public List<StudentInfo> findByCriteria(StudentSearchCriteria studentSearchCriteria) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(updateQuery(studentSearchCriteria))) {
                return StudentRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding students by criteria", ex);
        }
    }

    private String updateQuery(StudentSearchCriteria studentSearchCriteria) {
        return Objects.nonNull(studentSearchCriteria.getFaculty()) ?
                FIND_ALL_QUERY + "WHERE students_info.faculty = \'" + studentSearchCriteria.getFaculty() + "\'" :
                Objects.nonNull(studentSearchCriteria.getSpeciality()) ?
                        FIND_ALL_QUERY + "WHERE students_info.speciality = \'" + studentSearchCriteria.getSpeciality() + "\'" :
                        FIND_ALL_QUERY + "WHERE students_info.year = " + studentSearchCriteria.getYear();
    }

    @Override
    public List<StudentInfo> findByGroup(Long groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE students_info.group_id = ?")) {
            statement.setLong(1, groupId);
            try (ResultSet rs = statement.executeQuery()) {
                return StudentRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding students by group's id = " + groupId, ex);
        }
    }

    @Override
    public void create(StudentInfo studentInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, studentInfo.getAdmissionYear());
            statement.setString(2, studentInfo.getFaculty());
            statement.setString(3, studentInfo.getSpeciality());
            statement.setLong(4, studentInfo.getUser().getId());
            statement.setLong(5, studentInfo.getGroup().getId());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    studentInfo.setId(key.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating student", ex);
        }
    }

    @Override
    public void update(StudentInfo studentInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, studentInfo.getAdmissionYear());
            statement.setString(2, studentInfo.getFaculty());
            statement.setString(3, studentInfo.getSpeciality());
            statement.setLong(4, studentInfo.getUser().getId());
            statement.setLong(5, studentInfo.getGroup().getId());
            statement.setLong(6, studentInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving student with id = " + studentInfo.getId(), ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting student with id = " + id, ex);
        }
    }

}
