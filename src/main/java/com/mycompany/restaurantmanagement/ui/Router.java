package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Manager;
import com.mycompany.restaurantmanagement.model.User;

import com.mycompany.restaurantmanagement.service.InvoiceService;
import com.mycompany.restaurantmanagement.service.MenuService;
import com.mycompany.restaurantmanagement.service.OrderDetailService;
import com.mycompany.restaurantmanagement.service.OrderService;
import com.mycompany.restaurantmanagement.service.PaymentService;
import com.mycompany.restaurantmanagement.service.TableService;
import com.mycompany.restaurantmanagement.service.InventoryService;
import com.mycompany.restaurantmanagement.service.CategoryService;

import java.util.Scanner;

public class Router {

    private MenuService menuService;

    private InventoryService inventoryService;

    private CategoryService categoryService;

    private TableService tableService;

    private OrderService orderService;

    private OrderDetailService orderDetailService;

    private InvoiceService invoiceService;

    private PaymentService paymentService;

    public Router(

             MenuService menuService,

            InventoryService inventoryService,

            CategoryService categoryService,

            TableService tableService,

            OrderService orderService,

            OrderDetailService orderDetailService,

            InvoiceService invoiceService,

            PaymentService paymentService

    ) {

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

                UIManager managerUI = new UIManager((Manager) user);

                managerUI.show();

                break;

            case WAREHOUSE:
                InventoryUI inventoryUI = new InventoryUI();
                break;

            case EMPLOYEE:

                OrderUI orderUI = new OrderUI(

                        tableService,

                        orderService,

                        orderDetailService

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