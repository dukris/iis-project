package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistsException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
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
    public List<UserInfo> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserInfo findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("User with id = " + id + " not found"));
    }

    @Override
    public UserInfo create(UserInfo userInfo) {
        userRepository.findByEmail(userInfo.getEmail())
                .ifPresent(el -> {
                    throw new ResourceAlreadyExistsException("User with email = " + userInfo.getEmail() + " already exists");
                });
        userRepository.create(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        findById(userInfo.getId());
        userRepository.save(userInfo);
        return userInfo;
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

}
