package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.User;
import com.mycompany.restaurantmanagement.repository.UserRepository;

public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        if (!user.checkPassword(password)) return null;
        return user;
    }
}