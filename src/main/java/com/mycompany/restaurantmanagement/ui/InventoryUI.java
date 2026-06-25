package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.service.CategoryService;
import com.mycompany.restaurantmanagement.service.InventoryService;
import com.mycompany.restaurantmanagement.service.MenuService;

import java.util.List;
import java.util.Scanner;

public class InventoryUI {

    private final Scanner scanner;
    private final CategoryService categoryService;
    private final MenuService menuService;
    private final InventoryService inventoryService;

    public InventoryUI(Scanner scanner,
            CategoryService categoryService,
            MenuService menuService,
            InventoryService inventoryService) {
        this.scanner = scanner;
        this.categoryService = categoryService;
        this.menuService = menuService;
        this.inventoryService = inventoryService;
    }

    // ─── Menu chính ───────────────────────────────────────────
    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n===== MENU & INVENTORY MANAGEMENT =====");
            System.out.println("1. Add menu item");
            System.out.println("2. Edit menu item");
            System.out.println("3. Delete menu item");
            System.out.println("4. View all menu items");
            System.out.println("5. Check stock");
            System.out.println("6. Restock item");
            System.out.println("7. View low stock items");
            System.out.println("8. Manage categories");
            System.out.println("0. Back to main menu");

            int choice = readInt("Your choice: ");
            switch (choice) {
                case 1:
                    addMenuItem();
                    break;
                case 2:
                    editMenuItem();
                    break;
                case 3:
                    deleteMenuItem();
                    break;
                case 4:
                    viewAllMenuItems();
                    break;
                case 5:
                    checkStock();
                    break;
                case 6:
                    restockItem();
                    break;
                case 7:
                    viewLowStockItems();
                    break;
                case 8:
                    manageCategories();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }

    // ─── Menu con quản lý danh mục ─────────────────────────────
    private void manageCategories() {
        boolean inLoop = true;
        while (inLoop) {
            System.out.println("\n--- Category Management ---");
            System.out.println("1. Add new category");
            System.out.println("2. Edit category");
            System.out.println("3. View all categories");
            System.out.println("0. Back");

            int choice = readInt("Your choice: ");
            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    editCategory();
                    break;
                case 3:
                    viewAllCategories();
                    break;
                case 0:
                    inLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    // =========================================================================
    // ─── LOGIC XỬ LÝ CATEGORY ────────────────────────────────────────────────
    // =========================================================================

    private void addCategory() {
        System.out.println("\n-- Add New Category --");
        String name = readString("Enter category name: ");
        String desc = readString("Enter description: ");
        Category c = categoryService.addCategory(name, desc);
        System.out.println("Category added successfully: " + c);
    }

    private void editCategory() {
        System.out.println("\n-- Edit Category --");
        viewAllCategories();
        int id = readInt("Enter category ID to edit: ");
        String name = readString("Enter new name: ");
        String desc = readString("Enter new description: ");

        if (categoryService.updateCategory(id, name, desc)) {
            System.out.println("Category updated successfully.");
        } else {
            System.out.println("Category not found.");
        }
    }

    private void viewAllCategories() {
        System.out.println("\n-- List of Categories --");
        List<Category> list = categoryService.getAll();
        if (list.isEmpty()) {
            System.out.println("No categories available.");
        } else {
            for (Category c : list) {
                System.out.println(c);
            }
        }
    }

    // =========================================================================
    // ─── LOGIC XỬ LÝ MENU ITEM ───────────────────────────────────────────────
    // =========================================================================

    private void addMenuItem() {
        System.out.println("\n-- Add Menu Item --");
        viewAllCategories();
        int catId = readInt("Select category ID: ");

        Category category = categoryService.getById(catId);
        if (category == null) {
            System.out.println("Invalid category ID.");
            return;
        }

        String name = readString("Enter item name: ");
        String desc = readString("Enter description: ");
        double price;
        try {
            price = Double.parseDouble(readString("Enter price: "));
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format.");
            return;
        }

        MenuItem item = menuService.addMenuItem(name, desc, price, category);
        System.out.println("Menu item added: " + item);

        int initQty = readInt("Enter initial stock quantity: ");
        int minQty = readInt("Enter alert minimum quantity: ");
        String unit = readString("Enter unit (e.g., portion, glass, bowl): ");
        inventoryService.addInventoryItem(item, initQty, minQty, unit);
        System.out.println("Inventory record created for this item.");
    }

    private void editMenuItem() {
        System.out.println("\n-- Edit Menu Item --");
        viewAllMenuItems();
        int id = readInt("Enter item ID to edit: ");
        String name = readString("Enter new name: ");
        double price;
        try {
            price = Double.parseDouble(readString("Enter new price: "));
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
            return;
        }

        if (menuService.updateMenuItem(id, name, price)) {
            System.out.println("Menu item updated successfully.");
        } else {
            System.out.println("Menu item not found.");
        }
    }

    private void deleteMenuItem() {
        System.out.println("\n-- Delete Menu Item --");
        viewAllMenuItems();
        int id = readInt("Enter item ID to delete: ");
        if (menuService.deleteMenuItem(id)) {
            System.out.println("Menu item deleted successfully.");
        } else {
            System.out.println("Menu item not found.");
        }
    }

    private void viewAllMenuItems() {
        System.out.println("\n-- Menu Items --");
        List<MenuItem> items = menuService.getAllMenuItems();
        if (items.isEmpty()) {
            System.out.println("No menu items available.");
        } else {
            for (MenuItem item : items)
                System.out.println(item);
        }
    }

    // =========================================================================
    // ─── LOGIC XỬ LÝ INVENTORY ───────────────────────────────────────────────
    // =========================================================================

    private void checkStock() {
        System.out.println("\n-- Inventory Stock --");
        List<InventoryItem> inventory = inventoryService.getAll();
        if (inventory.isEmpty()) {
            System.out.println("No inventory records found.");
            return;
        }
        for (InventoryItem i : inventory) {
            String status = i.isOutOfStock() ? "[OUT OF STOCK]"
                    : i.isLowStock() ? "[LOW STOCK]  "
                            : "[OK]         ";
            System.out.printf("%-15s %s%n", status, i);
        }
    }

    private void restockItem() {
        System.out.println("\n-- Restock Item --");
        checkStock();

        int id, qty;
        try {
            id = readInt("Enter inventory ID to restock: ");
            qty = readInt("Quantity to add: ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        if (inventoryService.restock(id, qty)) {
            System.out.println("Restocked successfully.");
        } else {
            System.out.println("Inventory record not found.");
        }
    }

    private void viewLowStockItems() {
        System.out.println("\n-- Low Stock Alert items --");
        List<InventoryItem> lowStock = inventoryService.getLowStockItems();
        if (lowStock.isEmpty()) {
            System.out.println("All items are well stocked!");
            return;
        }
        for (InventoryItem i : lowStock) {
            System.out.println(i);
        }
    }

    // =========================================================================
    // ─── HELPER METHODS ──────────────────────────────────────────────────────
    // =========================================================================

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}