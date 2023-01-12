package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.StudentRepository;
import com.solvd.laba.iis.domain.criteria.StudentSearchCriteria;
import com.solvd.laba.iis.persistence.mapper.StudentRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private static final String FIND_ALL_QUERY = """
            SELECT students_info.id as student_id, students_info.year as student_year, students_info.faculty as student_faculty, students_info.speciality as student_speciality,
            users_info.id as user_id,  users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            groups.id as group_id, groups.number as group_number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users_info ON (students_info.user_id = users_info.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)""";
    private static final String FIND_BY_ID_QUERY = """
            SELECT students_info.id as student_id, students_info.year as student_year, students_info.faculty as student_faculty, students_info.speciality as student_speciality,
            users_info.id as user_id,  users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            groups.id as group_id, groups.number as group_number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users_info ON (students_info.user_id = users_info.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE students_info.id = ?""";
    private static final String FIND_BY_GROUP_QUERY = """
            SELECT students_info.id as student_id, students_info.year as student_year, students_info.faculty as student_faculty, students_info.speciality as student_speciality,
            users_info.id as user_id,  users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            groups.id as group_id, groups.number as group_number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users_info ON (students_info.user_id = users_info.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE students_info.group_id = ?""";

    private static final String FIND_BY_CRITERIA_QUERY = """
            SELECT students_info.id as student_id, students_info.year as student_year, students_info.faculty as student_faculty, students_info.speciality as student_speciality,
            users_info.id as user_id,  users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role,
            groups.id as group_id, groups.number as group_number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users_info ON (students_info.user_id = users_info.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id) """;
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.students_info (year, faculty, speciality, user_id, group_id) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.students_info WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.students_info SET year = ?, faculty = ?, speciality = ?, user_id = ?, group_id = ? WHERE id = ?";
    private final DataSource dataSource;

    @Override
    public List<StudentInfo> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return StudentRowMapper.mapStudents(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all students");
        }
    }

    @Override
    public Optional<StudentInfo> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(StudentRowMapper.mapStudent(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding student by id = " + id);
        }
    }

    @Override
    public List<StudentInfo> findByCriteria(StudentSearchCriteria studentSearchCriteria) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(updateQuery(studentSearchCriteria))) {
                return StudentRowMapper.mapStudents(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding students by criteria");
        }
    }

    private String updateQuery(StudentSearchCriteria studentSearchCriteria) {
        return Objects.nonNull(studentSearchCriteria.getFaculty()) ?
                FIND_BY_CRITERIA_QUERY + "WHERE students_info.faculty = \'" + studentSearchCriteria.getFaculty() + "\'" :
                Objects.nonNull(studentSearchCriteria.getSpeciality()) ?
                        FIND_BY_CRITERIA_QUERY + "WHERE students_info.speciality = \'" + studentSearchCriteria.getSpeciality() + "\'" :
                        FIND_BY_CRITERIA_QUERY + "WHERE students_info.year = " + studentSearchCriteria.getYear();
    }

    @Override
    public List<StudentInfo> findByGroup(long groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_QUERY)) {
            statement.setLong(1, groupId);
            try (ResultSet rs = statement.executeQuery()) {
                return StudentRowMapper.mapStudents(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding students by group's id = " + groupId);
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
            throw new ResourceMappingException("Exception occurred while creating student");
        }
    }

    @Override
    public void save(StudentInfo studentInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setInt(1, studentInfo.getAdmissionYear());
            statement.setString(2, studentInfo.getFaculty());
            statement.setString(3, studentInfo.getSpeciality());
            statement.setLong(4, studentInfo.getUser().getId());
            statement.setLong(5, studentInfo.getGroup().getId());
            statement.setLong(6, studentInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving student with id = " + studentInfo.getId());
        }
    }

    @Override
    public void delete(StudentInfo studentInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, studentInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting student with id = " + studentInfo.getId());
        }
    }

}
