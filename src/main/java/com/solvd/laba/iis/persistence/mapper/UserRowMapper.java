package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Role;
import com.solvd.laba.iis.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRowMapper {
    @SneakyThrows
    public static Optional<User> mapUser(ResultSet rs) {
        User user = new User();
        while (rs.next()) {
            user.setId(rs.getLong(1));
            user.setName(rs.getString(2));
            user.setSurname(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setPassword(rs.getString(5));
            user.setRole(Role.valueOf(rs.getString(6).toUpperCase()));
        }
        return Optional.of(user);
    }

    @SneakyThrows
    public static List<User> mapUsers(ResultSet rs) {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong(1));
            user.setName(rs.getString(2));
            user.setSurname(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setPassword(rs.getString(5));
            user.setRole(Role.valueOf(rs.getString(6).toUpperCase()));
            users.add(user);
        }
        return users;
    }
}
