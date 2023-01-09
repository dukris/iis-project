package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.Role;
import com.solvd.laba.iis.domain.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class UserRowMapper {
    @SneakyThrows
    public static Optional<User> mapUser(ResultSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("user_name"));
        user.setSurname(rs.getString("user_surname"));
        user.setEmail(rs.getString("user_email"));
        user.setPassword(rs.getString("user_password"));
        user.setRole(Role.valueOf(rs.getString("user_role").toUpperCase()));
        return Optional.of(user);
    }

    @SneakyThrows
    public static List<User> mapUsers(ResultSet rs) {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = mapUser(rs).orElseThrow();
            users.add(user);
        }
        return users;
    }
}
