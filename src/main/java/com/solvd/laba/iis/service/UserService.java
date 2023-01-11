package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<UserInfo> getAll();

    UserInfo getById(long id);

    @Transactional
    UserInfo create(UserInfo userInfo);

    @Transactional
    UserInfo save(UserInfo userInfo);

    @Transactional
    void delete(UserInfo userInfo);

}
