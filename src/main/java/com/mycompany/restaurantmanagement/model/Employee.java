package com.mycompany.restaurantmanagement.model;

public class Employee extends User {
    public Employee(int userId, String username, String password, String fullName, String email) {
        super(userId, username, password, fullName, email, Role.EMPLOYEE);
    }
}