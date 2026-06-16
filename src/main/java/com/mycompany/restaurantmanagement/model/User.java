package com.mycompany.restaurantmanagement.model;

public abstract class User {

    protected int userId;
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;
    protected boolean isActive;

    public User() {
    }

    public User(int userId, String username, String password,
                String fullName, String email, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password)
                && this.isActive;
    }

    public void logout() {
        System.out.println(fullName + " logged out.");
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", active=" + isActive +
                '}';
    }
}