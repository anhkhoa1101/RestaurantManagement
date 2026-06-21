package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.*;

public class Router {
    public void route(User user) {
        switch (user.getRole()) {
            case MANAGER:
                new UIManager((Manager) user).show();
                break;
//            case EMPLOYEE:
//                new UIEmployee((Employee) user).show();
//                break;
//            case CASHIER:
//                new UICashier((Cashier) user).show();
//                break;
//            default:
//                System.out.println("Role không hợp lệ.");
        }
    }
}