package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(long id);

    @Transactional
    User create(User user);

    @Transactional
    User save(User user);

    @Transactional
    void delete(User user);

}
