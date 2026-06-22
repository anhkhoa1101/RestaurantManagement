package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Manager;
import com.mycompany.restaurantmanagement.model.User;

import com.mycompany.restaurantmanagement.service.MenuService;
import com.mycompany.restaurantmanagement.service.OrderDetailService;
import com.mycompany.restaurantmanagement.service.OrderService;
import com.mycompany.restaurantmanagement.service.TableService;

public class Router {

    private TableService tableService;

    private OrderService orderService;

    private OrderDetailService orderDetailService;

    private MenuService menuService;

    public Router(
            TableService tableService,
            OrderService orderService,
            OrderDetailService orderDetailService,
            MenuService menuService
    ) {
        this.tableService = tableService;

        this.orderService = orderService;

        this.orderDetailService = orderDetailService;

        this.menuService = menuService;
    }

    public void route(User user) {

        if (user == null) {
            System.out.println("User không tồn tại.");
            return;
        }

        switch (user.getRole()) {
            case MANAGER:
                new UIManager((Manager) user).show();
                break;

            case EMPLOYEE:
                new OrderUI(orderService, orderDetailService, menuService).start();
                break;

            case CASHIER:

                System.out.println("Payment UI chưa triển khai.");
                break;

            default:
                System.out.println("Role không hợp lệ.");
        }
    }
}