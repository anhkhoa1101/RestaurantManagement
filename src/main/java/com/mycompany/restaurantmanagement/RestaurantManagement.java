package com.mycompany.restaurantmanagement;
//Repository
import  com.mycompany.restaurantmanagement.model.Manager;

import com.mycompany.restaurantmanagement.repository.UserRepository;

import com.mycompany.restaurantmanagement.repository.CategoryRepository;
import com.mycompany.restaurantmanagement.repository.InventoryRepository;
import com.mycompany.restaurantmanagement.repository.MenuItemRepository;

import com.mycompany.restaurantmanagement.repository.OrderRepository;
import com.mycompany.restaurantmanagement.repository.TableRepository;

import com.mycompany.restaurantmanagement.repository.InvoiceRepository;
import com.mycompany.restaurantmanagement.repository.PaymentRepository;

//Service
import com.mycompany.restaurantmanagement.service.*;

import com.mycompany.restaurantmanagement.ui.LoginUI;
import com.mycompany.restaurantmanagement.ui.Router;


import java.util.Scanner;

public class RestaurantManagement {

    public static void main(String[] args) {

        // ==========================
        // Repository Layer
        // ==========================
//      Member 1
        UserRepository userRepository = new UserRepository();

        UserService userService = new UserService(userRepository);

        AuthService authService = new AuthService(userRepository);

//        Member 2
        CategoryRepository categoryRepository = new CategoryRepository();

        MenuItemRepository menuRepository = new MenuItemRepository(categoryRepository);

        InventoryRepository inventoryRepository = new InventoryRepository(menuRepository);
//      Member 3
        TableRepository tableRepository = new TableRepository();

        OrderRepository orderRepository = new OrderRepository();
//      Member 4
        InvoiceRepository invoiceRepository = new InvoiceRepository();

        PaymentRepository paymentRepository = new PaymentRepository();

        Scanner scanner = new Scanner(System.in);

        // ==========================
        // Service Layer
        // ==========================

        MenuService menuService = new MenuService(menuRepository);

        InventoryService inventoryService = new InventoryService(inventoryRepository);

        CategoryService categoryService = new CategoryService(categoryRepository);

        TableService tableService = new TableService(tableRepository);

        InvoiceService invoiceService = new InvoiceService(invoiceRepository);

        OrderService orderService = new OrderService(orderRepository, tableService, inventoryService, invoiceService);

        OrderDetailService orderDetailService = new OrderDetailService(inventoryService, orderService);

        PaymentService paymentService = new PaymentService(paymentRepository, invoiceService);
        // ==========================
        // UI Layer
        // ==========================

        Router router = new Router(scanner, userService, menuService, inventoryService, categoryService, tableService, orderService, orderDetailService, invoiceService, paymentService);

        LoginUI loginUI = new LoginUI(authService, router);

        loginUI.show();
    }
}
