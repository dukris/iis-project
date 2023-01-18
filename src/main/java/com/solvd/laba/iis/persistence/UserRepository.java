package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {

    List<UserInfo> findAll();

    Optional<UserInfo> findById(Long id);

    boolean isExist(String email);

    void create(UserInfo userInfo);

    void update(UserInfo userInfo);

    void delete(Long id);

}
