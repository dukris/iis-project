package com.solvd.laba.iis.persistence.mapper;

import com.solvd.laba.iis.domain.UserInfo;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class UserRowMapper {

    @SneakyThrows
    public static UserInfo mapRow(ResultSet rs) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(rs.getLong("user_id"));
        userInfo.setName(rs.getString("user_name"));
        userInfo.setSurname(rs.getString("user_surname"));
        userInfo.setEmail(rs.getString("user_email"));
        userInfo.setPassword(rs.getString("user_password"));
        userInfo.setRole(UserInfo.Role.valueOf(rs.getString("user_role").toUpperCase()));
        return userInfo;
    }

    @SneakyThrows
    public static List<UserInfo> mapRows(ResultSet rs) {
        List<UserInfo> userInfos = new ArrayList<>();
        while (rs.next()) {
            UserInfo userInfo = mapRow(rs);
            userInfos.add(userInfo);
        }
        return userInfos;
    }

}
