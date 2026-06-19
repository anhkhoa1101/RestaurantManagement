package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.User;
import com.mycompany.restaurantmanagement.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;
    private User currentUser;

    // Dependency Injection: Truyền Repository vào Service khi khởi tạo
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.currentUser = null;
    }

    /**
     * Xử lý đăng nhập
     */
    public User login(String username, String password) {
        // Bước 1: Gọi Repository để truy xuất dữ liệu thay vì tự vòng lặp
        User user = userRepository.findByUsername(username);

        // Bước 2: Xử lý các quy tắc nghiệp vụ (Business Logic)
        if (user == null) {
            System.out.println("❌ Đăng nhập thất bại: Tài khoản không tồn tại.");
            return null;
        }

        if (!user.isActive()) {
            System.out.println("❌ Đăng nhập thất bại: Tài khoản đã bị khóa.");
            return null;
        }

        // Gọi hàm login từ Model User để đối chiếu mật khẩu
        if (user.login(username, password)) {
            this.currentUser = user;
            System.out.println("✅ Đăng nhập thành công! Xin chào, " + user.getFullName());
            return user;
        } else {
            System.out.println("❌ Đăng nhập thất bại: Sai mật khẩu.");
            return null;
        }
    }

    /**
     * Xử lý đăng xuất
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("👋 Tạm biệt, " + currentUser.getFullName());
            this.currentUser = null;
        } else {
            System.out.println("⚠️ Bạn chưa đăng nhập.");
        }
    }

    /**
     * Khóa hoặc mở khóa tài khoản
     */
    public boolean toggleAccountStatus(int userId, boolean status) {
        // Gọi Repository để tìm chính xác User cần cập nhật
        User user = userRepository.findById(userId);

        if (user != null) {
            user.setActive(status);
            String action = status ? "mở khóa" : "khóa";
            System.out.println("✅ Đã " + action + " tài khoản: " + user.getUsername());
            return true;
        }

        System.out.println("❌ Không tìm thấy người dùng với ID: " + userId);
        return false;
    }

    /**
     * Lấy thông tin user đang đăng nhập hiện tại
     */
    public User getCurrentUser() {
        return currentUser;
    }
}