package com.example.demo.user.service;

import com.example.demo.user.entity.User;

import java.util.List;

public interface UserService {

    public List<User> findByEmailAndFlag(String email, String flag);

    public List<User> findByEmail(String email);
}
