package com.mycompany.restaurantmanagement.model;

public class Cashier extends User {
    public Cashier(int userId, String username, String password, String fullName, String email) {
        super(userId, username, password, fullName, email, Role.CASHIER);
    }
}