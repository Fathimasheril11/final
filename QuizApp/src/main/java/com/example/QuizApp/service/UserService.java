package com.example.QuizApp.service;

import com.example.QuizApp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(User user);
    User findByUsername(String username);
}
