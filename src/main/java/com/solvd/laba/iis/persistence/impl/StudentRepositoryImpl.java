package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.StudentRepository;
import com.solvd.laba.iis.persistence.mapper.StudentRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {
    private final DataSource dataSource;
    private static final String FIND_ALL_QUERY = """
            SELECT students_info.id, students_info.year, students_info.faculty, students_info.speciality,
            users.id,  users.name, users.surname, users.email, users.password, users.role,
            groups.id, groups.number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id);""";
    private static final String FIND_BY_ID_QUERY = """
            SELECT students_info.id, students_info.year, students_info.faculty, students_info.speciality,
            users.id,  users.name, users.surname, users.email, users.password, users.role,
            groups.id, groups.number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE students_info.id = ?""";
    private static final String FIND_BY_GROUP_QUERY = """
            SELECT students_info.id, students_info.year, students_info.faculty, students_info.speciality,
            users.id,  users.name, users.surname, users.email, users.password, users.role,
            groups.id, groups.number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE students_info.group_id = ?""";

    private static final String FIND_BY_FACULTY_QUERY = """
            SELECT students_info.id, students_info.year, students_info.faculty, students_info.speciality,
            users.id,  users.name, users.surname, users.email, users.password, users.role,
            groups.id, groups.number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE students_info.faculty = ?""";

    private static final String FIND_BY_SPECIALITY_QUERY = """
            SELECT students_info.id, students_info.year, students_info.faculty, students_info.speciality,
            users.id,  users.name, users.surname, users.email, users.password, users.role,
            groups.id, groups.number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE students_info.speciality = ?""";

    private static final String FIND_BY_YEAR_QUERY = """
            SELECT students_info.id, students_info.year, students_info.faculty, students_info.speciality,
            users.id,  users.name, users.surname, users.email, users.password, users.role,
            groups.id, groups.number
            FROM iis_schema.students_info
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE students_info.year = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.students_info (year, faculty, speciality, user_id, group_id) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.students_info WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.students_info SET year = ?, faculty = ?, speciality = ?, user_id = ?, group_id = ? WHERE id = ?";

    @Override
    public List<StudentInfo> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);
            return StudentRowMapper.mapStudents(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all students");
        }
    }

    @Override
    public Optional<StudentInfo> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            return StudentRowMapper.mapStudent(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding student by id = " + id);
        }
    }

    @Override
    public List<StudentInfo> findByGroup(long groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_QUERY)) {
            statement.setLong(1, groupId);
            ResultSet rs = statement.executeQuery();
            return StudentRowMapper.mapStudents(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding students by group's id = " + groupId);
        }
    }

    @Override
    public List<StudentInfo> findBySpeciality(String speciality) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SPECIALITY_QUERY)) {
            statement.setString(1, speciality);
            ResultSet rs = statement.executeQuery();
            return StudentRowMapper.mapStudents(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding students by speciality = " + speciality);
        }
    }

    @Override
    public List<StudentInfo> findByFaculty(String faculty) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_FACULTY_QUERY)) {
            statement.setString(1, faculty);
            ResultSet rs = statement.executeQuery();
            return StudentRowMapper.mapStudents(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding students by faculty = " + faculty);
        }
    }

    @Override
    public List<StudentInfo> findByAdmissionYear(int year) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_YEAR_QUERY)) {
            statement.setInt(1, year);
            ResultSet rs = statement.executeQuery();
            return StudentRowMapper.mapStudents(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding students by year = " + year);
        }
    }

    @Override
    public StudentInfo create(StudentInfo studentInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, studentInfo.getAdmissionYear());
            statement.setString(2, studentInfo.getFaculty());
            statement.setString(3, studentInfo.getSpeciality());
            statement.setLong(4, studentInfo.getUser().getId());
            statement.setLong(5, studentInfo.getGroup().getId());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            if (key.next()) {
                studentInfo.setId(key.getLong(1));
            }
            return studentInfo;
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating student");
        }
    }

    @Override
    public StudentInfo save(StudentInfo studentInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setInt(1, studentInfo.getAdmissionYear());
            statement.setString(2, studentInfo.getFaculty());
            statement.setString(3, studentInfo.getSpeciality());
            statement.setLong(4, studentInfo.getUser().getId());
            statement.setLong(5, studentInfo.getGroup().getId());
            statement.setLong(6, studentInfo.getId());
            statement.executeUpdate();
            return studentInfo;
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
