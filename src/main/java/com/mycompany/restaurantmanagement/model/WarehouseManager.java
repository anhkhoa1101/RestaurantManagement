package com.mycompany.restaurantmanagement.model;

public class WarehouseManager extends User{
    public WarehouseManager(int userId, String username, String password, String fullName, String email) {
        super(userId, username, password, fullName, email, Role.WAREHOUSE);
    }
}
