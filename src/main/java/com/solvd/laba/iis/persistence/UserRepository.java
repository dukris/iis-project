package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<UserInfo> findAll();

    Optional<UserInfo> findById(Long id);

    Optional<UserInfo> findByEmail(String email);

    void create(UserInfo userInfo);

    void save(UserInfo userInfo);

    void delete(Long id);


}
