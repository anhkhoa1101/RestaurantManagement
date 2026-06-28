package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.*;
import com.mycompany.restaurantmanagement.repository.UserRepository;
import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(int id, String username, String password, String fullName,
                           String email, Role role) {
        if (userRepository.findByUsername(username) != null) {
            System.out.println("❌ Username đã tồn tại!");
            return;
        }
        User newUser;
        switch (role) {
            case MANAGER:  newUser = new Manager(id, username, password, fullName, email); break;
            case EMPLOYEE: newUser = new Employee(id, username, password, fullName, email); break;
            case CASHIER:  newUser = new Cashier(id, username, password, fullName, email); break;
            default: throw new IllegalArgumentException("Role không hợp lệ");
        }
        userRepository.save(newUser);
        System.out.println("✅ Đã tạo tài khoản: " + username);
    }

    public void updateUserInfo(int userId, String newFullName, String newEmail) {
        User user = findById(userId);
        if (user == null) {
            System.out.println("❌ Không tìm thấy tài khoản!");
            return;
        }
        user.setFullName(newFullName);
        user.setEmail(newEmail);
        userRepository.update(user);
        System.out.println("✅ Đã cập nhật tài khoản: " + user.getUsername());
    }

    public void deleteUser(int userId) {
        userRepository.delete(userId);
        System.out.println("✅ Đã xoá tài khoản có ID: " + userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(int userId) {
        for (User u : userRepository.findAll()) {
            if (u.getUserId() == userId) return u;
        }
        return null;
    }

    public boolean existsByUsername(String username) {

        for (User user : userRepository.findAll()) {

            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }
}