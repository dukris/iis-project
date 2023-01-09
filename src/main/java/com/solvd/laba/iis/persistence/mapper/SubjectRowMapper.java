package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Subject;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectRowMapper {
    @SneakyThrows
    public static Optional<Subject> mapSubject(ResultSet rs) {
        Subject subject = new Subject();
        while (rs.next()) {
            subject.setId(rs.getLong(1));
            subject.setName(rs.getString(2));
        }
        return Optional.of(subject);
    }

    @SneakyThrows
    public static List<Subject> mapSubjects(ResultSet rs) {
        List<Subject> subjects = new ArrayList<>();
        while (rs.next()) {
            Subject subject = new Subject();
            subject.setId(rs.getLong(1));
            subject.setName(rs.getString(2));
            subjects.add(subject);
        }
        return subjects;
    }
}
