package com.solvd.laba.iis.persistence.jdbc.mapper;

import com.solvd.laba.iis.domain.Group;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class GroupRowMapper {

    @SneakyThrows
    public static Group mapRow(ResultSet rs) {
        Group group = new Group();
        group.setId(rs.getLong("group_id"));
        group.setNumber(rs.getInt("group_number"));
        return group;
    }

    @SneakyThrows
    public static List<Group> mapRows(ResultSet rs) {
        List<Group> groups = new ArrayList<>();
        while (rs.next()) {
            Group group = mapRow(rs);
            groups.add(group);
        }
        return groups;
    }

}
