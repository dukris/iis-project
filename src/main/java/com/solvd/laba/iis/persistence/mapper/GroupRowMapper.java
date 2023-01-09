package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Group;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupRowMapper{

    @SneakyThrows
    public static Optional<Group> mapGroup(ResultSet rs){
        Group group = new Group();
        while (rs.next()) {
            group.setId(rs.getLong(1));
            group.setNumber(rs.getInt(2));
        }
        return Optional.of(group);
    }

    @SneakyThrows
    public static List<Group> mapGroups(ResultSet rs){
        List<Group> groups = new ArrayList<>();
        while (rs.next()) {
            Group group = new Group();
            group.setId(rs.getLong(1));
            group.setNumber(rs.getInt(2));
            groups.add(group);
        }
        return groups;
    }
}
