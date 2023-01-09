package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.Role;
import com.solvd.laba.iis.domain.StudentInfo;
import com.solvd.laba.iis.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRowMapper {
    @SneakyThrows
    public static Optional<StudentInfo> mapStudent(ResultSet rs){
        StudentInfo studentInfo = new StudentInfo();
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
    }

    @SneakyThrows
    public static List<StudentInfo> mapStudents(ResultSet rs){
        List<StudentInfo> students = new ArrayList<>();
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
    }
}
