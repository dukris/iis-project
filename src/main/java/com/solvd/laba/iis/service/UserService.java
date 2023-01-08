package com.solvd.laba.iis.service;

import com.solvd.laba.iis.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    User getById(long id);

    User create(User user);

    User save(User user);

    void delete(User user);
}
