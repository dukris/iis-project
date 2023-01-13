package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<UserInfo> findAll();

    UserInfo findById(Long id);

    @Transactional
    UserInfo create(UserInfo userInfo);

    @Transactional
    UserInfo save(UserInfo userInfo);

    @Transactional
    void delete(Long id);

}
