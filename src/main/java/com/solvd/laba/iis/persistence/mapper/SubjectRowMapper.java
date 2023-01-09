package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Subject;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class SubjectRowMapper {
    @SneakyThrows
    public static Optional<Subject> mapSubject(ResultSet rs) {
        Subject subject = new Subject();
        subject.setId(rs.getLong("subject_id"));
        subject.setName(rs.getString("subject_name"));
        return Optional.of(subject);
    }

    @SneakyThrows
    public static List<Subject> mapSubjects(ResultSet rs) {
        List<Subject> subjects = new ArrayList<>();
        while (rs.next()) {
            Subject subject = mapSubject(rs).orElseThrow();
            subjects.add(subject);
        }
        return subjects;
    }
}
