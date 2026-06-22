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

    // ─── Menu chính ───────────────────────────────────────────────────────────

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

            switch (scanner.nextLine().trim()) {
                case "1":
                    addMenuItem();
                    break;
                case "2":
                    editMenuItem();
                    break;
                case "3":
                    deleteMenuItem();
                    break;
                case "4":
                    searchMenuItem();
                    break;
                case "5":
                    viewAllMenuItems();
                    break;
                case "6":
                    checkStock();
                    break;
                case "7":
                    restockItem();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ─── Helper: đọc số nguyên an toàn ──────────────────────────────────────

    private int readInt(String prompt) throws NumberFormatException {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private double readDouble(String prompt) throws NumberFormatException {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine().trim());
    }

    // ─── Add menu item ────────────────────────────────────────────────────────

    private void addMenuItem() {
        System.out.println("\n-- Add Menu Item --");

        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories available. Please add a category first.");
            return;
        }
        System.out.println("Available categories:");
        for (Category c : categories) {
            System.out.printf("  [%d] %s%n", c.getId(), c.getName());
        }

        int categoryId;
        try {
            categoryId = readInt("Category ID: ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        Optional<Category> category = categoryService.getCategoryById(categoryId);
        if (!category.isPresent()) {
            System.out.println("Category not found.");
            return;
        }

        System.out.print("Item name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        double price;
        try {
            price = readDouble("Price (VND): ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
            return;
        }

        MenuItem item = menuService.addMenuItem(name, description, price, category.get());

        int quantity, minQuantity;
        try {
            quantity = readInt("Initial stock quantity: ");
            minQuantity = readInt("Minimum stock threshold: ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        System.out.print("Unit (e.g. kg, litre, phan): ");
        String unit = scanner.nextLine().trim();

        inventoryService.addInventoryItem(item, quantity, minQuantity, unit);
        System.out.println("Menu item added successfully: " + item);
    }

    // ─── Edit menu item ───────────────────────────────────────────────────────

    private void editMenuItem() {
        System.out.println("\n-- Edit Menu Item --");
        viewAllMenuItems();

        int id;
        try {
            id = readInt("Enter item ID to edit: ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        System.out.print("New name: ");
        String name = scanner.nextLine().trim();

        double price;
        try {
            price = readDouble("New price: ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
            return;
        }

        System.out.print("Confirm update? (Y/N): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("Update cancelled.");
            return;
        }

        System.out.println(menuService.updateMenuItem(id, name, price)
                ? "Item updated successfully."
                : "Item not found.");
    }

    // ─── Delete menu item ─────────────────────────────────────────────────────

    private void deleteMenuItem() {
        System.out.println("\n-- Delete Menu Item --");
        viewAllMenuItems();

        int id;
        try {
            id = readInt("Enter item ID to delete: ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        System.out.print("Confirm delete? (Y/N): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        System.out.println(menuService.deleteMenuItem(id)
                ? "Item deleted successfully."
                : "Item not found.");
    }

    // ─── Search menu items ────────────────────────────────────────────────────

    private void searchMenuItem() {
        System.out.println("\n-- Search Menu Items --");
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().trim();

        List<MenuItem> results = menuService.searchMenuItems(keyword);
        if (results.isEmpty()) {
            System.out.println("No items found matching '" + keyword + "'.");
        } else {
            for (MenuItem m : results)
                System.out.println(m);
        }
    }

    // ─── View all menu items ──────────────────────────────────────────────────

    private void viewAllMenuItems() {
        System.out.println("\n-- All Menu Items --");
        List<MenuItem> items = menuService.getAllMenuItems();
        if (items.isEmpty()) {
            System.out.println("No menu items available.");
        } else {
            for (MenuItem item : items)
                System.out.println(item);
        }
    }

    // ─── Check stock ──────────────────────────────────────────────────────────

    private void checkStock() {
        System.out.println("\n-- Inventory Stock --");
        List<InventoryItem> inventory = inventoryService.getAllInventoryItems();
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

    // ─── Restock item ─────────────────────────────────────────────────────────

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

        System.out.println(inventoryService.restock(id, qty)
                ? "Restocked successfully."
                : "Inventory item not found.");
    }
}
