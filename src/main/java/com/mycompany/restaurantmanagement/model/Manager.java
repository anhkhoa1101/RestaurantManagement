package com.mycompany.restaurantmanagement.model;

public class Manager extends User {
    public Manager(int userId, String username, String password, String fullName, String email) {
        super(userId, username, password, fullName, email, Role.MANAGER);
    }
}