package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(long id);

    @Transactional
    User create(User user);

    @Transactional
    User save(User user);

    @Transactional
    void delete(User user);
}
