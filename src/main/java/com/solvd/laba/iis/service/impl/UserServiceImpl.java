package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.domain.exception.ServiceException;
import com.solvd.laba.iis.persistence.UserRepository;
import com.solvd.laba.iis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException("User with id = " + id + "not found"));
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
