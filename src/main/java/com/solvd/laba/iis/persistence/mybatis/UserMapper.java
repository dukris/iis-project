package com.solvd.laba.iis.persistence.mybatis;

import com.solvd.laba.iis.domain.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    //TODO add mapper.xml

    List<UserInfo> findAll();

    Optional<UserInfo> findById(Long id);

    boolean isExist(String email);

    void create(UserInfo userInfo);

    void update(UserInfo userInfo);

    void delete(Long id);


}
