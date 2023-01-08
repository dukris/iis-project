package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.*;
import com.solvd.laba.iis.domain.exception.DaoException;
import com.solvd.laba.iis.persistence.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarkRepositoryImpl implements MarkRepository {
    private final DataSource dataSource;
    private static final String FIND_BY_SUBJECT_AND_TEACHER_QUERY = """
            SELECT marks.id, marks.date, marks.value, marks.student_id,
            students_info.year, students_info.faculty, students_info.speciality,
            users.id, users.name, users.surname, users.email, users.password, users.role,
            groups.id, groups.number,
            subjects.id, subjects.name
            FROM iis_schema.marks
            LEFT JOIN iis_schema.subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN iis_schema.students_info ON (marks.student_id = students_info.id)
            LEFT JOIN iis_schema.users ON (students_info.user_id = users.id)
            LEFT JOIN iis_schema.groups ON (students_info.group_id = groups.id)
            WHERE subjects.id = ? AND marks.teacher_id = ?""";
    private static final String FIND_BY_STUDENT_QUERY = """
            SELECT marks.id, marks.date, marks.value, marks.teacher_id,
            users.id, users.name, users.surname, users.email, users.password, users.role,
            subjects.id, subjects.name
            FROM iis_schema.marks
            LEFT JOIN iis_schema.subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN iis_schema.teachers_info ON (marks.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.students_info ON (marks.teacher_id = students_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE marks.student_id = ?""";

    private static final String FIND_BY_STUDENT_AND_SUBJECT_QUERY = """
            SELECT marks.id, marks.date, marks.value, marks.teacher_id,
            users.id, users.name, users.surname, users.email, users.password, users.role,
            subjects.id, subjects.name
            FROM iis_schema.marks
            LEFT JOIN iis_schema.subjects ON (marks.subject_id = subjects.id)
            LEFT JOIN iis_schema.teachers_info ON (marks.teacher_id = teachers_info.id)
            LEFT JOIN iis_schema.students_info ON (marks.teacher_id = students_info.id)
            LEFT JOIN iis_schema.users ON (teachers_info.user_id = users.id)
            WHERE marks.student_id = ? AND marks.subject_id = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.marks (date, value, student_id, teacher_id, subject_id) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.marks WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.lessons SET date = ?, value = ?, student_id = ?, teacher_id = ?, subject_id = ? WHERE id = ?";

    @Override
    public List<Mark> findBySubjectAndTeacher(long subjectId, long teacherId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SUBJECT_AND_TEACHER_QUERY)) {
            statement.setLong(1, subjectId);
            statement.setLong(2, teacherId);
            ResultSet rs = statement.executeQuery();
            List<Mark> marks = new ArrayList<>();
            while (rs.next()) {
                Mark mark = new Mark();
                mark.setId(rs.getLong(1));
                mark.setDate(rs.getDate(2).toLocalDate());
                mark.setValue(rs.getInt(3));

                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(rs.getLong(4));
                studentInfo.setAdmissionYear(rs.getInt(5));
                studentInfo.setFaculty(rs.getString(6));
                studentInfo.setSpeciality(rs.getString(7));

                User user = new User();
                user.setId(rs.getLong(8));
                user.setName(rs.getString(9));
                user.setSurname(rs.getString(10));
                user.setEmail(rs.getString(11));
                user.setPassword(rs.getString(12));
                user.setRole(Role.valueOf(rs.getString(13).toUpperCase()));
                studentInfo.setUser(user);

                Group group = new Group();
                group.setId(rs.getLong(14));
                group.setNumber(rs.getInt(15));
                studentInfo.setGroup(group);
                mark.setStudent(studentInfo);

                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setId(teacherId);
                mark.setTeacher(teacherInfo);

                Subject subject = new Subject();
                subject.setId(rs.getLong(16));
                subject.setName(rs.getString(17));
                mark.setSubject(subject);

                marks.add(mark);
            }
            return marks;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding marks by teacher's id = " + teacherId + " and subject's id = " + subjectId);
        }
    }

    @Override
    public List<Mark> findByStudent(long studentId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_STUDENT_QUERY)) {
            statement.setLong(1, studentId);
            ResultSet rs = statement.executeQuery();
            List<Mark> marks = new ArrayList<>();
            while (rs.next()) {
                Mark mark = new Mark();
                mark.setId(rs.getLong(1));
                mark.setDate(rs.getDate(2).toLocalDate());
                mark.setValue(rs.getInt(3));

                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setId(rs.getLong(4));

                User user = new User();
                user.setId(rs.getLong(5));
                user.setName(rs.getString(6));
                user.setSurname(rs.getString(7));
                user.setEmail(rs.getString(8));
                user.setPassword(rs.getString(9));
                user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
                teacherInfo.setUser(user);
                mark.setTeacher(teacherInfo);

                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(studentId);
                mark.setStudent(studentInfo);

                Subject subject = new Subject();
                subject.setId(rs.getLong(11));
                subject.setName(rs.getString(12));
                mark.setSubject(subject);

                marks.add(mark);
            }
            return marks;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding marks by student's id = " + studentId);
        }
    }

    @Override
    public List<Mark> findByStudentAndSubject(long studentId, long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_STUDENT_AND_SUBJECT_QUERY)) {
            statement.setLong(1, studentId);
            statement.setLong(2, subjectId);
            ResultSet rs = statement.executeQuery();
            List<Mark> marks = new ArrayList<>();
            while (rs.next()) {
                Mark mark = new Mark();
                mark.setId(rs.getLong(1));
                mark.setDate(rs.getDate(2).toLocalDate());
                mark.setValue(rs.getInt(3));

                TeacherInfo teacherInfo = new TeacherInfo();
                teacherInfo.setId(rs.getLong(4));

                User user = new User();
                user.setId(rs.getLong(5));
                user.setName(rs.getString(6));
                user.setSurname(rs.getString(7));
                user.setEmail(rs.getString(8));
                user.setPassword(rs.getString(9));
                user.setRole(Role.valueOf(rs.getString(10).toUpperCase()));
                teacherInfo.setUser(user);
                mark.setTeacher(teacherInfo);

                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setId(studentId);
                mark.setStudent(studentInfo);

                Subject subject = new Subject();
                subject.setId(rs.getLong(11));
                subject.setName(rs.getString(12));
                mark.setSubject(subject);

                marks.add(mark);
            }
            return marks;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding marks by student's id = " + studentId + " and subject's id = " + subjectId);
        }
    }

    @Override
    public Mark create(Mark mark) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
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
            throw new DaoException("Exception occurred while creating mark");
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
            throw new DaoException("Exception occurred while saving mark");
        }
    }

    @Override
    public void delete(Mark mark) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, mark.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while deleting mark with id = " + mark.getId());
        }
    }
}
