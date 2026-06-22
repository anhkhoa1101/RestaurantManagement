/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.restaurantmanagement;

import com.mycompany.restaurantmanagement.repository.*;
import com.mycompany.restaurantmanagement.service.*;
import com.mycompany.restaurantmanagement.ui.*;

import java.util.Scanner;

/**
 *
 * @author khoa0
 */
public class RestaurantManagement {

    public static void main(String[] args) {


        // ── Khởi tạo Repository (đúng thứ tự phụ thuộc) ────────────────────
        // CategoryRepository không phụ thuộc ai — khởi tạo trước
        CategoryRepository categoryRepo = new CategoryRepository();
        // MenuItemRepository cần CategoryRepository để resolve Category khi đọc file
        MenuItemRepository menuRepo = new MenuItemRepository(categoryRepo);
        // InventoryRepository cần MenuItemRepository để resolve MenuItem khi đọc file
        InventoryRepository inventoryRepo = new InventoryRepository(menuRepo);

        // ── Khởi tạo Service ────────────────────────────────────────────────
        CategoryService categoryService = new CategoryService(categoryRepo);
        MenuService menuService = new MenuService(menuRepo);
        InventoryService inventoryService = new InventoryService(inventoryRepo);

        // ── Khởi tạo UI ─────────────────────────────────────────────────────
        Scanner scanner = new Scanner(System.in);
        InventoryUI inventoryUI = new InventoryUI(
                scanner,
                categoryService,
                menuService,
                inventoryService);

        // ── Chạy ứng dụng ───────────────────────────────────────────────────
        System.out.println("=================================================");
        System.out.println("  Bingchiling Restaurant Management System");
        System.out.println("  Module 1: User Authentication & Authorization");
        System.out.println("  Module 2: Inventory & Product Management");
        System.out.println("  Module 3: Order Management");
        System.out.println("=================================================");
        inventoryUI.show();

        System.out.println("Goodbye!");
        scanner.close();
    }
}
