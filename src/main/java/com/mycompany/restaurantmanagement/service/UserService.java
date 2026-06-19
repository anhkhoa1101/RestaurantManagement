package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Cashier;
import com.mycompany.restaurantmanagement.model.Employee;
import com.mycompany.restaurantmanagement.model.Manager;
import com.mycompany.restaurantmanagement.model.User;
import com.mycompany.restaurantmanagement.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * [Member 1] Lớp xử lý logic nghiệp vụ cho đăng nhập / xác thực / phân quyền.
 * UI (UserUI.java) sẽ gọi qua lớp này, không trực tiếp gọi UserRepository.
 */
public class UserService {

    private final UserRepository userRepository;
    private User currentUser; // user đang đăng nhập (session hiện tại)

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userRepository.loadFromFile();
    }

    // ===================================================================
    // AUTHENTICATION
    // ===================================================================

    /**
     * Đăng nhập hệ thống. Trả về User nếu thành công, null nếu sai thông tin
     * hoặc tài khoản bị khóa.
     */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("[UserService] Tài khoản '" + username + "' không tồn tại.");
            return null;
        }
        if (!user.isActive()) {
            System.out.println("[UserService] Tài khoản '" + username + "' đã bị khóa.");
            return null;
        }
        if (!user.login(username, password)) {
            System.out.println("[UserService] Sai mật khẩu.");
            return null;
        }
        this.currentUser = user;
        System.out.println("[UserService] Đăng nhập thành công: " + user);
        return user;
    }

    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
            currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Kiểm tra quyền hạn theo role, dùng trước khi cho phép thực hiện hành động nhạy cảm
     * (ví dụ: chỉ Manager mới được tạo/xóa tài khoản nhân viên khác).
     */
    public boolean isCurrentUserManager() {
        return currentUser instanceof Manager;
    }

    // ===================================================================
    // CRUD — chỉ Manager được phép tạo/sửa/xóa tài khoản (theo bảng phân quyền README)
    // ===================================================================

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    public Manager createManager(String username, String password, String fullName,
                                 String email, int managerId, String department) {
        validateNewUsername(username);
        int newUserId = userRepository.generateNextUserId();
        Manager manager = new Manager(newUserId, username, password, fullName, email, true,
                managerId, department);
        userRepository.add(manager);
        userRepository.saveToFile();
        return manager;
    }

    public Employee createEmployee(String username, String password, String fullName,
                                   String email, int employeeId, String position,
                                   double salary, LocalDate hireDate) {
        validateNewUsername(username);
        int newUserId = userRepository.generateNextUserId();
        Employee employee = new Employee(newUserId, username, password, fullName, email, true,
                employeeId, position, salary, hireDate);
        userRepository.add(employee);
        userRepository.saveToFile();
        return employee;
    }

    public Cashier createCashier(String username, String password, String fullName,
                                 String email, int cashierId,
                                 LocalDateTime shiftStart, LocalDateTime shiftEnd) {
        validateNewUsername(username);
        int newUserId = userRepository.generateNextUserId();
        Cashier cashier = new Cashier(newUserId, username, password, fullName, email, true,
                cashierId, shiftStart, shiftEnd);
        userRepository.add(cashier);
        userRepository.saveToFile();
        return cashier;
    }

    /**
     * Khóa / mở tài khoản (setActive). Tự động lưu file sau khi thay đổi.
     */
    public boolean setUserActive(int userId, boolean active) {
        User user = userRepository.findById(userId);
        if (user == null) {
            System.out.println("[UserService] Không tìm thấy user ID " + userId);
            return false;
        }
        user.setActive(active);
        userRepository.saveToFile();
        return true;
    }

    public boolean resetPassword(int userId, String newPassword) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return false;
        }
        user.setPassword(newPassword);
        userRepository.saveToFile();
        return true;
    }

    public boolean deleteUser(int userId) {
        boolean removed = userRepository.remove(userId);
        if (removed) {
            userRepository.saveToFile();
        }
        return removed;
    }

    private void validateNewUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username '" + username + "' đã tồn tại.");
        }
    }
}