package com.solvd.laba.iis.persistence.jdbc.mapper;

import com.solvd.laba.iis.domain.Subject;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class SubjectRowMapper {

    @SneakyThrows
    public static Subject mapRow(ResultSet rs) {
        Subject subject = new Subject();
        subject.setId(rs.getLong("subject_id"));
        subject.setName(rs.getString("subject_name"));
        return subject;
    }

    @SneakyThrows
    public static List<Subject> mapRows(ResultSet rs) {
        List<Subject> subjects = new ArrayList<>();
        while (rs.next()) {
            Subject subject = mapRow(rs);
            subjects.add(subject);
        }
        return subjects;
    }

}
