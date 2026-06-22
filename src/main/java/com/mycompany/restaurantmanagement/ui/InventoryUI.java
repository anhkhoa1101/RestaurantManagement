/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.ui;

/**
 *
 * @author khoa0
 */
package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.service.CategoryService;
import com.mycompany.restaurantmanagement.service.InventoryService;
import com.mycompany.restaurantmanagement.service.MenuService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InventoryUI {

    private final Scanner          scanner;
    private final CategoryService  categoryService;
    private final MenuService      menuService;
    private final InventoryService inventoryService;

    public InventoryUI(Scanner scanner,
                       CategoryService categoryService,
                       MenuService menuService,
                       InventoryService inventoryService) {
        this.scanner          = scanner;
        this.categoryService  = categoryService;
        this.menuService      = menuService;
        this.inventoryService = inventoryService;
    }

    /** Hiển thị menu module và điều hướng theo lựa chọn người dùng. */
    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n===== MENU & INVENTORY MANAGEMENT =====");
            System.out.println("1. Add menu item");
            System.out.println("2. Edit menu item");
            System.out.println("3. Delete menu item");
            System.out.println("4. Search menu items");
            System.out.println("5. View all menu items");
            System.out.println("6. Check stock");
            System.out.println("7. Restock item");
            System.out.println("0. Back");
            System.out.print("Select: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addMenuItem();     
                break;
                case "2": editMenuItem();    
                break;
                case "3": deleteMenuItem();  
                break;
                case "4": searchMenuItem();  
                break;
                case "5": viewAllMenuItems();
                break;
                case "6": checkStock();      
                break;
                case "7": restockItem();     
                break;
                case "0": running = false;   
                break;
                default:  
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ─── Add menu item ────────────────────────────────────────────────────────

    // Cho phép người dùng nhập thông tin món ăn mới, sau đó tạo món ăn và bản ghi tồn kho liên kết.
    private void addMenuItem() {
        System.out.println("\n-- Add Menu Item --");

        // Hiển thị danh sách danh mục để người dùng chọn
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories available. Please add a category first.");
            return;
        }
        System.out.println("Available categories:");
        for (Category c : categories) {
            System.out.printf("  [%d] %s%n", c.getId(), c.getName());
        }

        System.out.print("Category ID: ");
        int categoryId = Integer.parseInt(scanner.nextLine().trim());
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        // Dừng lại nếu không tìm thấy danh mục
        if (!category.isPresent()) { 
            System.out.println("Category not found."); 
            return; }

        System.out.print("Item name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        // Tạo món ăn qua MenuService
        MenuItem item = menuService.addItem(name, description, price, category.get());

        // Tạo bản ghi tồn kho liên kết với món vừa tạo
        System.out.print("Initial stock quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Minimum stock threshold: ");
        int minQuantity = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Unit (e.g. kg, litre, portion): ");
        String unit = scanner.nextLine().trim();

        inventoryService.addInventoryItem(item, quantity, minQuantity, unit);
        System.out.println("Menu item added successfully: " + item);
    }

    // ─── Edit menu item ───────────────────────────────────────────────────────

    // Cho phép người dùng nhập ID món ăn muốn sửa, sau đó nhập thông tin mới,
    private void editMenuItem() {
        System.out.println("\n-- Edit Menu Item --");
        // Hiển thị danh sách để người dùng chọn ID
        viewAllMenuItems();
 
        System.out.print("Enter item ID to edit: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("New name: ");
        String name = scanner.nextLine().trim();
        System.out.print("New price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
 
        // Xác nhận trước khi lưu thay đổi
        System.out.print("Confirm update? (Y/N): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Update cancelled.");
            return;
        }
 
        // Gọi service cập nhật — service sẽ ghi lại file txt sau khi sửa
        boolean updated = menuService.updateItem(id, name, price);
        System.out.println(updated ? "Item updated successfully." : "Item not found.");
    }

    // ─── Delete menu item ─────────────────────────────────────────────────────

    private void deleteMenuItem() {
        System.out.println("\n-- Delete Menu Item --");
        // Hiển thị danh sách để người dùng chọn ID
        viewAllMenuItems();
 
        System.out.print("Enter item ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
 
        // Xác nhận trước khi xóa — tránh xóa nhầm
        System.out.print("Confirm delete? (Y/N): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }
 
        boolean deleted = menuService.deleteItem(id);
        System.out.println(deleted ? "Item deleted successfully." : "Item not found.");
    }

    // ─── Search menu items ────────────────────────────────────────────────────

    // Tìm kiếm món ăn theo tên (hoặc một phần tên) và hiển thị kết quả.
    private void searchMenuItem() {
        System.out.println("\n-- Search Menu Items --");
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().trim();

        List<MenuItem> results = menuService.searchItems(keyword);
        if (results.isEmpty()) {
            System.out.println("No items found matching '" + keyword + "'.");
        } else {
            for (MenuItem m : results) System.out.println(m);
        }
    }

    // ─── View all menu items ──────────────────────────────────────────────────

    private void viewAllMenuItems() {
        System.out.println("\n-- All Menu Items --");
        List<MenuItem> items = menuService.getAllItems();
        if (items.isEmpty()) {
            System.out.println("No menu items available.");
        } else {
            for (MenuItem item : items) System.out.println(item);
        }
    }

    // ─── Check stock ──────────────────────────────────────────────────────────

    // Hiển thị danh sách tồn kho hiện tại, kèm trạng thái (OK / LOW STOCK / OUT OF STOCK).
    private void checkStock() {
        System.out.println("\n-- Inventory Stock --");
        List<InventoryItem> inventory = inventoryService.getAllInventory();
        if (inventory.isEmpty()) {
            System.out.println("No inventory records found.");
            return;
        }
        for (InventoryItem i : inventory) {
            // Xác định trạng thái kho để hiển thị bên cạnh thông tin
            String status = i.isOutOfStock() ? "[OUT OF STOCK]"
                          : i.isLowStock()   ? "[LOW STOCK]  "
                          :                    "[OK]         ";
            System.out.printf("%-15s %s%n", status, i);
        }
    }

    // ─── Restock item ─────────────────────────────────────────────────────────

    // Nhập ID của mặt hàng tồn kho cần nhập thêm, và số lượng muốn thêm vào.
    private void restockItem() {
        System.out.println("\n-- Restock Item --");
        // Hiển thị tồn kho hiện tại để người dùng chọn ID
        checkStock();

        System.out.print("Enter inventory ID to restock: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Quantity to add: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        // Service sẽ tự ghi lại file txt sau khi nhập hàng thành công
        boolean restocked = inventoryService.restock(id, qty);
        System.out.println(restocked ? "Restocked successfully." : "Inventory item not found.");
    }
}