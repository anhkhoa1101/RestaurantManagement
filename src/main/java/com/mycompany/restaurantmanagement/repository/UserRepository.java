package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.User;
import java.util.List;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
    List<User> findAll();
    void update(User user);
    void delete(int userId);
}