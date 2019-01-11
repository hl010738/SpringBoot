package com.example.demo.user.service.impl;

import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepositroy;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositroy userRepositroy;

    @Override
    public List<User> findByEmailAndFlag(String email, String flag) {
        return userRepositroy.findByEmailAndFlag(email, flag);
    }

    @Override
    public List<User> findByEmail(String email) {
        return this.findByEmailAndFlag(email, "0");
    }
}
