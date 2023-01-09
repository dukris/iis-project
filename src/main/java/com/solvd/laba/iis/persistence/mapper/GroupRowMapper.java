package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Group;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GroupRowMapper {

    @SneakyThrows
    public static Optional<Group> mapGroup(ResultSet rs) {
        Group group = new Group();
        group.setId(rs.getLong("group_id"));
        group.setNumber(rs.getInt("group_number"));
        return Optional.of(group);
    }

    @SneakyThrows
    public static List<Group> mapGroups(ResultSet rs) {
        List<Group> groups = new ArrayList<>();
        while (rs.next()) {
            Group group = mapGroup(rs).orElseThrow();
            groups.add(group);
        }
        return groups;
    }
}
