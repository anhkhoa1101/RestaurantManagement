
package com.mycompany.restaurantmanagement;
//Repository
import com.mycompany.restaurantmanagement.repository.UserRepository;
import com.mycompany.restaurantmanagement.repository.UserRepository_File;

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

        UserRepository userRepository = new UserRepository_File();

        AuthService authService = new AuthService(userRepository);

        CategoryRepository categoryRepository = new CategoryRepository();

        MenuItemRepository menuRepository = new MenuItemRepository(categoryRepository);

        InventoryRepository inventoryRepository = new InventoryRepository(menuRepository);

        TableRepository tableRepository = new TableRepository();

        OrderRepository orderRepository = new OrderRepository();

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

        OrderService orderService = new OrderService(orderRepository ,tableService, inventoryService);

        OrderDetailService orderDetailService = new OrderDetailService( inventoryService, orderService );

        InvoiceService invoiceService = new InvoiceService(invoiceRepository, orderService);

        PaymentService paymentService = new PaymentService(paymentRepository, invoiceService);
                
        // ==========================
        // UI Layer
        // ==========================

        Router router = new Router(scanner, menuService, inventoryService, categoryService, tableService, orderService, orderDetailService, invoiceService, paymentService);

        LoginUI loginUI = new LoginUI(authService, router);

        loginUI.show();
    }
}
