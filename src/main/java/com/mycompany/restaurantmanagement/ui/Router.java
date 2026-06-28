package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Manager;
import com.mycompany.restaurantmanagement.model.User;

import com.mycompany.restaurantmanagement.service.*;

import java.util.Scanner;

public class Router {

    private UserService userService;

    private MenuService menuService;

    private InventoryService inventoryService;

    private CategoryService categoryService;

    private TableService tableService;

    private OrderService orderService;

    private OrderDetailService orderDetailService;

    private InvoiceService invoiceService;

    private PaymentService paymentService;

    private Scanner scanner;



    public Router(

            Scanner scanner,

            UserService userService,

            MenuService menuService,

            InventoryService inventoryService,

            CategoryService categoryService,

            TableService tableService,

            OrderService orderService,

            OrderDetailService orderDetailService,

            InvoiceService invoiceService,

            PaymentService paymentService

    ) {

        this.scanner = scanner;

        this.userService = userService;

        this.menuService = menuService;

        this.inventoryService = inventoryService;

        this.categoryService = categoryService;

        this.tableService = tableService;

        this.orderService = orderService;

        this.orderDetailService = orderDetailService;

        this.menuService = menuService;

        this.invoiceService = invoiceService;

        this.paymentService = paymentService;

    }

    public void route(User user) {

        if (user == null) {

            System.out.println("User không tồn tại.");

            return;

        }

        switch (user.getRole()) {

            case MANAGER:

                UIManager managerUI = new UIManager(userService);

                managerUI.show();

                break;

            case WAREHOUSE:
                InventoryUI inventoryUI = new InventoryUI(scanner, categoryService, menuService, inventoryService);
                inventoryUI.show();
                break;

            case EMPLOYEE:

                OrderUI orderUI = new OrderUI(

                        tableService,

                        orderService,

                        menuService

                );
                orderUI.run();

                break;

            case CASHIER:

                PaymentUI paymentUI = new PaymentUI( invoiceService, paymentService);

                paymentUI.run();

                break;

            default:

                System.out.println("Role không hợp lệ.");

        }

    }

}