
package com.mycompany.restaurantmanagement;
//Repository
import com.mycompany.restaurantmanagement.repository.CategoryRepository;
import com.mycompany.restaurantmanagement.repository.InventoryRepository;
import com.mycompany.restaurantmanagement.repository.MenuItemRepository;
import com.mycompany.restaurantmanagement.repository.OrderRepository;
import com.mycompany.restaurantmanagement.repository.TableRepository;
import com.mycompany.restaurantmanagement.repository.UserRepository;
import com.mycompany.restaurantmanagement.repository.UserRepository_File;
//Service
import com.mycompany.restaurantmanagement.service.AuthService;
import com.mycompany.restaurantmanagement.service.InventoryService;
import com.mycompany.restaurantmanagement.service.MenuService;
import com.mycompany.restaurantmanagement.service.OrderDetailService;
import com.mycompany.restaurantmanagement.service.OrderService;
import com.mycompany.restaurantmanagement.service.TableService;

import com.mycompany.restaurantmanagement.ui.LoginUI;
import com.mycompany.restaurantmanagement.ui.Router;

public class RestaurantManagement {

    public static void main(String[] args) {

        // ==========================
        // Repository Layer
        // ==========================

        UserRepository userRepository = new UserRepository_File();

        CategoryRepository categoryRepository = new CategoryRepository();

        MenuItemRepository menuRepository = new MenuItemRepository(categoryRepository);

        InventoryRepository inventoryRepository = new InventoryRepository(menuRepository);

        TableRepository tableRepository = new TableRepository();

        OrderRepository orderRepository = new OrderRepository();

        // ==========================
        // Service Layer
        // ==========================

        AuthService authService = new AuthService(userRepository);

        MenuService menuService = new MenuService(menuRepository);

        InventoryService inventoryService = new InventoryService(inventoryRepository);

        TableService tableService = new TableService(tableRepository);

        OrderService orderService = new OrderService(orderRepository, tableService, inventoryService);

        OrderDetailService orderDetailService =
                new OrderDetailService(
                        inventoryService
                );

        // ==========================
        // UI Layer
        // ==========================

        Router router = new Router(tableService, orderService, orderDetailService, menuService);

        LoginUI loginUI = new LoginUI(authService, router);

        loginUI.show();
    }
}
