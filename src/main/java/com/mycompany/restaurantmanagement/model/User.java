package com.mycompany.restaurantmanagement.model;

/**
 * [Member 1] Lớp cha trừu tượng cho tất cả người dùng trong hệ thống.
 * Manager, Employee, Cashier đều kế thừa từ lớp này.
 */
public abstract class User {

    protected int userId;
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;
    protected boolean isActive;

    public User(int userId, String username, String password, String fullName, String email, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
    }

    // ===== Getters / Setters =====

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        // Lưu ý: chỉ dùng nội bộ để kiểm tra đăng nhập / lưu file,
        // không expose ra UI dưới dạng hiển thị cho người dùng khác.
        return password;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // ===== Business methods =====

    /**
     * Kiểm tra đăng nhập: so khớp username + password và tài khoản phải đang active.
     */
    public boolean login(String username, String password) {
        if (!this.isActive) {
            return false;
        }
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        // Trong console app, logout chỉ cần thông báo + UI quay lại màn hình đăng nhập.
        System.out.println("[" + getRole() + "] " + fullName + " đã đăng xuất.");
    }

    /**
     * Mỗi lớp con phải định nghĩa vai trò của mình (dùng cho phân quyền & hiển thị).
     */
    public abstract String getRole();

    /**
     * Mỗi lớp con tự định nghĩa cách chuyển đổi thành 1 dòng để lưu file.
     * Format chung: dùng dấu '|' để phân tách field, để tránh xung đột với dấu ','
     * có thể xuất hiện trong họ tên / địa chỉ email, v.v.
     */
    public abstract String toFileLine();

    @Override
    public String toString() {
        return String.format(
                "[%d] %-10s | %-15s | %-20s | %-25s | %s",
                userId, getRole(), username, fullName, email, isActive ? "ACTIVE" : "LOCKED"
        );
    }
}