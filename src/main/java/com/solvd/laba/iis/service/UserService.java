package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.UserInfo;

import java.util.List;

public interface UserService {

    List<UserInfo> retrieveAll();

    UserInfo retrieveById(Long id);

    UserInfo retrieveByEmail(String email);

    UserInfo create(UserInfo userInfo);

    UserInfo update(UserInfo userInfo);

    void delete(Long id);

}
