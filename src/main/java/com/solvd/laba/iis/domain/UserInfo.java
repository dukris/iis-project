package com.solvd.laba.iis.domain;

import lombok.*;

@Data
public class UserInfo {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;

    public enum Role {

        TEACHER,
        STUDENT,
        ADMIN

    }

}
