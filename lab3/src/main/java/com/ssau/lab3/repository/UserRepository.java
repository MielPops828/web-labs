package com.ssau.lab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssau.lab3.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByLogin(String login);
}
