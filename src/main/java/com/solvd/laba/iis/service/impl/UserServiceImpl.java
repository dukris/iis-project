package com.solvd.laba.iis.service.impl;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.exception.ResourceAlreadyExistsException;
import com.solvd.laba.iis.domain.exception.ResourceDoesNotExistException;
import com.solvd.laba.iis.persistence.UserRepository;
import com.solvd.laba.iis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserInfo> retrieveAll() {
        return userRepository.findAll();
    }

    @Override
    public UserInfo retrieveById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("User with id = " + id + " not found"));
    }

    @Override
    @Transactional
    public UserInfo create(UserInfo userInfo) {
        if (userRepository.isExist(userInfo.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email = " + userInfo.getEmail() + " already exists");
        }
        userRepository.create(userInfo);
        return userInfo;
    }

    @Override
    @Transactional
    public UserInfo update(UserInfo userInfo) {
        UserInfo foundUser = retrieveById(userInfo.getId());
        foundUser.setName(userInfo.getName());
        foundUser.setSurname(userInfo.getSurname());
        foundUser.setEmail(userInfo.getEmail());
        foundUser.setPassword(userInfo.getPassword());
        foundUser.setRole(userInfo.getRole());
        userRepository.update(userInfo);
        return userInfo;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

}
