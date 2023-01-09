package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class StudentRowMapper {
    @SneakyThrows
    public static Optional<StudentInfo> mapStudent(ResultSet rs) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setId(rs.getLong("student_id"));
        studentInfo.setAdmissionYear(rs.getInt("student_year"));
        studentInfo.setFaculty(rs.getString("student_faculty"));
        studentInfo.setSpeciality(rs.getString("student_speciality"));
        User student = UserRowMapper.mapUser(rs).orElseThrow();
        studentInfo.setUser(student);
        Group group = GroupRowMapper.mapGroup(rs).orElseThrow();
        studentInfo.setGroup(group);
        return Optional.of(studentInfo);
    }

    @SneakyThrows
    public static List<StudentInfo> mapStudents(ResultSet rs) {
        List<StudentInfo> students = new ArrayList<>();
        while (rs.next()) {
            StudentInfo studentInfo = mapStudent(rs).orElseThrow();
            students.add(studentInfo);
        }
        return students;
    }
}
