package com.solvd.laba.iis.persistence;

import com.solvd.laba.iis.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(long id);

    User create(User user);

    User save(User user);

    void delete(User user);

}
