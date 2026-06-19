package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    // Create: Thêm user mới
    public void save(User user) {
        users.add(user);
    }

    // Read: Tìm user theo Username
    public User findByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // Read: Tìm user theo ID (Dùng cho chức năng khóa/mở khóa)
    public User findById(int userId) {
        for (User u : users) {
            if (u.getUserId() == userId) {
                return u;
            }
        }
        return null;
    }

    // Read: Lấy toàn bộ danh sách
    public List<User> findAll() {
        return users;
    }
}