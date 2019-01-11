package com.example.demo.user.repository;

import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositroy extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    List<User> findByEmailAndFlag(String email, String flag);
}
