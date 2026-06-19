package com.mycompany.restaurantmanagement.model;

public abstract class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;
    protected boolean isActive;

    public User(int userId, String username, String password, String fullName, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.isActive = true; // Mặc định là true khi tạo mới
    }

    // Getters
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

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // Methods
    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername)
                && this.password.equals(inputPassword)
                && this.isActive;
    }

    public void logout() {
        // Trong hệ thống console, hàm này thường in ra thông báo
        // Việc xoá session sẽ do UserService xử lý
        System.out.println("Tài khoản " + this.username + " đang tiến hành đăng xuất...");
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Username: %s | Tên: %s | Email: %s | Trạng thái: %s",
                userId, username, fullName, email, (isActive ? "Hoạt động" : "Bị khóa"));
    }
}