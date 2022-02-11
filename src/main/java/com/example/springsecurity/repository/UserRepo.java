package com.example.springsecurity.repository;

import com.example.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findOneByUserId(String userId);
    User  findByUserId(String login);
}
