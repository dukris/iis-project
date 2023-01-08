package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.Role;
import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.domain.exception.DaoException;
import com.solvd.laba.iis.persistence.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
            List<StudentInfo> students = new ArrayList<>();
            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);
            while (rs.next()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(rs.getLong(1));
                studentInfo.setAdmissionYear(rs.getInt(2));
                studentInfo.setFaculty(rs.getString(3));
                studentInfo.setSpeciality(rs.getString(4));

                User user = new User();
                user.setId(rs.getLong(5));
                user.setName(rs.getString(6));
                user.setSurname(rs.getString(7));
                user.setEmail(rs.getString(8));
                user.setPassword(rs.getString(9));
                user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
                studentInfo.setUser(user);

                Group group = new Group();
                group.setId(rs.getLong(11));
                group.setNumber(rs.getInt(12));
                studentInfo.setGroup(group);

                students.add(studentInfo);
            }
            return students;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding all students");
        }
    }

    @Override
    public Optional<StudentInfo> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            StudentInfo studentInfo = new StudentInfo();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                studentInfo.setId(rs.getLong(1));
                studentInfo.setAdmissionYear(rs.getInt(2));
                studentInfo.setFaculty(rs.getString(3));
                studentInfo.setSpeciality(rs.getString(4));

                User user = new User();
                user.setId(rs.getLong(5));
                user.setName(rs.getString(6));
                user.setSurname(rs.getString(7));
                user.setEmail(rs.getString(8));
                user.setPassword(rs.getString(9));
                user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
                studentInfo.setUser(user);

                Group group = new Group();
                group.setId(rs.getLong(11));
                group.setNumber(rs.getInt(12));
                studentInfo.setGroup(group);
            }
            return Optional.of(studentInfo);
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding student by id = " + id);
        }
    }

    @Override
    public List<StudentInfo> findByGroup(long groupId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GROUP_QUERY)) {
            statement.setLong(1, groupId);
            List<StudentInfo> students = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(rs.getLong(1));
                studentInfo.setAdmissionYear(rs.getInt(2));
                studentInfo.setFaculty(rs.getString(3));
                studentInfo.setSpeciality(rs.getString(4));

                User user = new User();
                user.setId(rs.getLong(5));
                user.setName(rs.getString(6));
                user.setSurname(rs.getString(7));
                user.setEmail(rs.getString(8));
                user.setPassword(rs.getString(9));
                user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
                studentInfo.setUser(user);

                Group group = new Group();
                group.setId(rs.getLong(11));
                group.setNumber(rs.getInt(12));
                studentInfo.setGroup(group);

                students.add(studentInfo);
            }
            return students;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding students by group's id = " + groupId);
        }
    }

    @Override
    public List<StudentInfo> findBySpeciality(String speciality) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SPECIALITY_QUERY)) {
            statement.setString(1, speciality);
            List<StudentInfo> students = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(rs.getLong(1));
                studentInfo.setAdmissionYear(rs.getInt(2));
                studentInfo.setFaculty(rs.getString(3));
                studentInfo.setSpeciality(rs.getString(4));

                User user = new User();
                user.setId(rs.getLong(5));
                user.setName(rs.getString(6));
                user.setSurname(rs.getString(7));
                user.setEmail(rs.getString(8));
                user.setPassword(rs.getString(9));
                user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
                studentInfo.setUser(user);

                Group group = new Group();
                group.setId(rs.getLong(11));
                group.setNumber(rs.getInt(12));
                studentInfo.setGroup(group);

                students.add(studentInfo);
            }
            return students;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding students by speciality = " + speciality);
        }
    }

    @Override
    public List<StudentInfo> findByFaculty(String faculty) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_FACULTY_QUERY)) {
            statement.setString(1, faculty);
            List<StudentInfo> students = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(rs.getLong(1));
                studentInfo.setAdmissionYear(rs.getInt(2));
                studentInfo.setFaculty(rs.getString(3));
                studentInfo.setSpeciality(rs.getString(4));

                User user = new User();
                user.setId(rs.getLong(5));
                user.setName(rs.getString(6));
                user.setSurname(rs.getString(7));
                user.setEmail(rs.getString(8));
                user.setPassword(rs.getString(9));
                user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
                studentInfo.setUser(user);

                Group group = new Group();
                group.setId(rs.getLong(11));
                group.setNumber(rs.getInt(12));
                studentInfo.setGroup(group);

                students.add(studentInfo);
            }
            return students;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding students by faculty = " + faculty);
        }
    }

    @Override
    public List<StudentInfo> findByAdmissionYear(int year) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_YEAR_QUERY)) {
            statement.setInt(1, year);
            List<StudentInfo> students = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(rs.getLong(1));
                studentInfo.setAdmissionYear(rs.getInt(2));
                studentInfo.setFaculty(rs.getString(3));
                studentInfo.setSpeciality(rs.getString(4));

                User user = new User();
                user.setId(rs.getLong(5));
                user.setName(rs.getString(6));
                user.setSurname(rs.getString(7));
                user.setEmail(rs.getString(8));
                user.setPassword(rs.getString(9));
                user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
                studentInfo.setUser(user);

                Group group = new Group();
                group.setId(rs.getLong(11));
                group.setNumber(rs.getInt(12));
                studentInfo.setGroup(group);

                students.add(studentInfo);
            }
            return students;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding students by year = " + year);
        }
    }

    @Override
    @Transactional
    public StudentInfo create(StudentInfo studentInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
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
            throw new DaoException("Exception occurred while creating student");
        }
    }

    @Override
    @Transactional
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
            throw new DaoException("Exception occurred while saving student with id = " + studentInfo.getId());
        }
    }

    @Override
    @Transactional
    public void delete(StudentInfo studentInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, studentInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while deleting student with id = " + studentInfo.getId());
        }
    }
}
