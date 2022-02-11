package com.example.springsecurity.service;

import com.example.springsecurity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.springsecurity.repository.UserRepo;

import java.util.ArrayList;


@Service
public class UserService implements UserDetailsService {

    private User user;

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        user = userRepo.findOneByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
                new ArrayList<>());
    }

    public User getUserByUserId(String login) {
        return userRepo.findByUserId(login);
    }

    public User getUser() {
        return user;
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }
}
