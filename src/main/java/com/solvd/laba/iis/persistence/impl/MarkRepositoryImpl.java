package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.Mark;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.MarkRepository;
import com.solvd.laba.iis.domain.criteria.MarkSearchCriteria;
import com.solvd.laba.iis.persistence.mapper.MarkRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MarkRepositoryImpl implements MarkRepository {

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
            FROM marks
            LEFT JOIN subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN students_info ON (marks.student_id = students_info.id)
            LEFT JOIN teachers_info ON (marks.teacher_id = teachers_info.id)
            LEFT JOIN groups ON (students_info.group_id = groups.id)
            LEFT JOIN users_info teacher on  (teacher.id = teachers_info.user_id)
            LEFT JOIN users_info student on (student.id = students_info.user_id) """;
    private static final String CREATE_QUERY = "INSERT INTO marks (date, value, student_id, teacher_id, subject_id) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM marks WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE marks SET date = ?, value = ?, student_id = ?, teacher_id = ?, subject_id = ? WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public List<Mark> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return MarkRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all marks", ex);
        }
    }

    @Override
    public Optional<Mark> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE marks.id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(MarkRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding mark by mark's id = " + id, ex);
        }
    }

    @Override
    public List<Mark> findBySubjectAndTeacher(Long subjectId, Long teacherId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE subjects.id = ? AND marks.teacher_id = ?")) {
            statement.setLong(1, subjectId);
            statement.setLong(2, teacherId);
            try (ResultSet rs = statement.executeQuery()) {
                return MarkRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding marks by teacher's id = " + teacherId + " and subject's id = " + subjectId, ex);
        }
    }

    @Override
    public List<Mark> findByCriteria(Long studentId, MarkSearchCriteria markSearchCriteria) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(updateQuery(studentId, markSearchCriteria))) {
                return MarkRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding marks by student's id = " + studentId, ex);
        }
    }

    private String updateQuery(Long studentId, MarkSearchCriteria markSearchCriteria) {
        return Objects.nonNull(markSearchCriteria.getSubjectId()) ?
                FIND_ALL_QUERY + "WHERE marks.student_id = " + studentId + " AND marks.subject_id = " + markSearchCriteria.getSubjectId() :
                FIND_ALL_QUERY + "WHERE marks.student_id = " + studentId;
    }

    @Override
    public void create(Mark mark) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(mark.getDate()));
            statement.setInt(2, mark.getValue());
            statement.setLong(3, mark.getStudent().getId());
            statement.setLong(4, mark.getTeacher().getId());
            statement.setLong(5, mark.getSubject().getId());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    mark.setId(key.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating mark", ex);
        }
    }

    @Override
    public void update(Mark mark) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setDate(1, Date.valueOf(mark.getDate()));
            statement.setInt(2, mark.getValue());
            statement.setLong(3, mark.getStudent().getId());
            statement.setLong(4, mark.getTeacher().getId());
            statement.setLong(5, mark.getSubject().getId());
            statement.setLong(6, mark.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving mark", ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting mark with id = " + id, ex);
        }
    }

}
