package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.MarkRepository;
import com.solvd.laba.iis.persistence.mapper.MarkRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MarkRepositoryImpl implements MarkRepository {
    private final DataSource dataSource;
    private static final String FIND_ALL_QUERY = """
            SELECT marks.id as mark_id, marks.date as mark_date, marks.value as mark_value, marks.student_id as student_id,
            students_info.year as student_year, students_info.faculty as student_faculty, students_info.speciality as student_speciality,
            student.id  as student_user_id, student.name as student_user_name, student.surname  as student_user_surname,
            student.email as student_user_email, student.password  as student_user_password, student.role as student_user_role,
            marks.teacher_id  as teacher_id,
            teacher.id  as teacher_user_id, teacher.name as teacher_user_name, teacher.surname  as teacher_user_surname,
            teacher.email as teacher_user_email, teacher.password as teacher_user_password, teacher.role as teacher_user_role,
            groups.id as group_id, groups.number as group_number,
            subjects.id as subject_id, subjects.name as subject_name
            FROM iis_schema.marks
            LEFT JOIN iis_schema.subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN iis_schema.students_info ON (marks.student_id = students_info.id)
            LEFT JOIN iis_schema.teachers_info ON (marks.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            LEFT JOIN iis_schema.users teacher on  (teacher.id = teachers_info.user_id)
            LEFT JOIN iis_schema.users student on (student.id = students_info.user_id)""";

    private static final String FIND_BY_ID_QUERY = """
            SELECT marks.id as mark_id, marks.date as mark_date, marks.value as mark_value, marks.student_id as student_id,
            students_info.year as student_year, students_info.faculty as student_faculty, students_info.speciality as student_speciality,
            student.id  as student_user_id, student.name as student_user_name, student.surname  as student_user_surname,
            student.email as student_user_email, student.password  as student_user_password, student.role as student_user_role,
            marks.teacher_id  as teacher_id,
            teacher.id  as teacher_user_id, teacher.name as teacher_user_name, teacher.surname  as teacher_user_surname,
            teacher.email as teacher_user_email, teacher.password as teacher_user_password, teacher.role as teacher_user_role,
            groups.id as group_id, groups.number as group_number,
            subjects.id as subject_id, subjects.name as subject_name
            FROM iis_schema.marks
            LEFT JOIN iis_schema.subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN iis_schema.students_info ON (marks.student_id = students_info.id)
            LEFT JOIN iis_schema.teachers_info ON (marks.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            LEFT JOIN iis_schema.users teacher on  (teacher.id = teachers_info.user_id)
            LEFT JOIN iis_schema.users student on (student.id = students_info.user_id)
            WHERE marks.id = ?""";
    private static final String FIND_BY_SUBJECT_AND_TEACHER_QUERY = """
            SELECT marks.id as mark_id, marks.date as mark_date, marks.value as mark_value, marks.student_id as student_id,
            students_info.year as student_year, students_info.faculty as student_faculty, students_info.speciality as student_speciality,
            users.id as student_user_id, users.name as student_user_name, users.surname as student_user_surname,
            users.email as student_user_email, users.password as student_user_password, users.role as student_user_role,
            groups.id as group_id, groups.number as group_number,
            subjects.id as subject_id, subjects.name as subject_name,
            marks.teacher_id as teacher_id
            FROM iis_schema.marks
            LEFT JOIN iis_schema.subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN iis_schema.students_info ON (marks.student_id = students_info.id)
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE subjects.id = ? AND marks.teacher_id = ?""";
    private static final String FIND_BY_STUDENT_QUERY = """
            SELECT marks.id as mark_id, marks.date as mark_date, marks.value as mark_value, marks.teacher_id as teacher_id,
            users.id as teacher_user_id, users.name as teacher_user_name, users.surname as teacher_user_surname,
            users.email as teacher_user_email, users.password as teacher_user_password, users.role as teacher_user_role,
            subjects.id as subject_id, subjects.name as subject_name,
            marks.student_id as student_id
            FROM iis_schema.marks
            LEFT JOIN iis_schema.subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN iis_schema.teachers_info ON (marks.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE marks.student_id = ?""";

    private static final String FIND_BY_STUDENT_AND_SUBJECT_QUERY = """
            SELECT marks.id as mark_id, marks.date as mark_date, marks.value as mark_value, marks.teacher_id as teacher_id,
            users.id as teacher_user_id, users.name as teacher_user_name, users.surname as teacher_user_surname,
            users.email as teacher_user_email, users.password as teacher_user_password, users.role as teacher_user_role,
            subjects.id as subject_id, subjects.name as subject_name,
            marks.student_id as student_id
            FROM iis_schema.marks
            LEFT JOIN iis_schema.subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN iis_schema.teachers_info ON (marks.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE marks.student_id = ? AND marks.subject_id = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.marks (date, value, student_id, teacher_id, subject_id) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.marks WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.marks SET date = ?, value = ?, student_id = ?, teacher_id = ?, subject_id = ? WHERE id = ?";

    @Override
    public List<Mark> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);
            return MarkRowMapper.mapMarks(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all marks");
        }
    }

    @Override
    public Optional<Mark> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return MarkRowMapper.mapMark(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding mark by mark's id = " + id);
        }
    }

    @Override
    public List<Mark> findBySubjectAndTeacher(long subjectId, long teacherId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SUBJECT_AND_TEACHER_QUERY)) {
            statement.setLong(1, subjectId);
            statement.setLong(2, teacherId);
            ResultSet rs = statement.executeQuery();
            return  MarkRowMapper.mapMarksBySubjectAndTeacher(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding marks by teacher's id = " + teacherId + " and subject's id = " + subjectId);
        }
    }

    @Override
    public List<Mark> findByStudent(long studentId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_STUDENT_QUERY)) {
            statement.setLong(1, studentId);
            ResultSet rs = statement.executeQuery();
            return MarkRowMapper.mapMarksByStudent(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding marks by student's id = " + studentId);
        }
    }

    @Override
    public List<Mark> findByStudentAndSubject(long studentId, long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_STUDENT_AND_SUBJECT_QUERY)) {
            statement.setLong(1, studentId);
            statement.setLong(2, subjectId);
            ResultSet rs = statement.executeQuery();
            return MarkRowMapper.mapMarksByStudent(rs);
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding marks by student's id = " + studentId + " and subject's id = " + subjectId);
        }
    }

    @Override
    public Mark create(Mark mark) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1,Date.valueOf(mark.getDate()));
            statement.setInt(2, mark.getValue());
            statement.setLong(3, mark.getStudent().getId());
            statement.setLong(4, mark.getTeacher().getId());
            statement.setLong(5, mark.getSubject().getId());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            if (key.next()) {
                mark.setId(key.getLong(1));
            }
            return mark;
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating mark");
        }
    }

    @Override
    public Mark save(Mark mark) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setDate(1,Date.valueOf(mark.getDate()));
            statement.setInt(2, mark.getValue());
            statement.setLong(3, mark.getStudent().getId());
            statement.setLong(4, mark.getTeacher().getId());
            statement.setLong(5, mark.getSubject().getId());
            statement.setLong(6, mark.getId());
            statement.executeUpdate();
            return mark;
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving mark");
        }
    }

    @Override
    public void delete(Mark mark) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, mark.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting mark with id = " + mark.getId());
        }
    }
}
