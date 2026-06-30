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

    // ─── Menu chính ────────────────────────────────────────────────────────────

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

    private int readInt(String prompt) throws NumberFormatException {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private double readDouble(String prompt) throws NumberFormatException {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine().trim());
    }

    // ─── Thêm mới món ăn
    // ────────────────────────────────────────────────────────────

    private void addMenuItem() {
        System.out.println("\n-- Add Menu Item --");

        // Gọi hàm getAll() chung của BaseService thay thế cho getAllCategories()
        List<Category> categories = categoryService.getAll();
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

        // Sử dụng getById() để lấy trực tiếp đối tượng Category thay vì Optional
        Category category = categoryService.getById(categoryId);
        if (category == null) {
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

        // Sử dụng addMenuItem() từ MenuService để tạo món ăn mới và tự động lưu vào
        // file
        MenuItem item = menuService.addMenuItem(name, description, price, category);

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
        if (menuService.getById(id) == null) {
            System.out.println("Item not found.");
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
        System.out.println("\nSelect Category:");
        System.out.println("1. Khai Vi");
        System.out.println("2. Mon Chinh");
        System.out.println("3. Trang Mieng");
        System.out.println("4. Do Uong");

        int categoryId;
        try {
            categoryId = readInt("Your choice (1-4): ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            return;
        }

        // Lấy object Category thật từ ID vừa nhập (1, 2, 3 hoặc 4)
        Category category = categoryService.getById(categoryId);
        if (category == null) {
            System.out.println("Category not found. Update cancelled.");
            return;
        }
        System.out.println("Confirm Available (True/False or T/F):");
        String input = scanner.nextLine().trim().toLowerCase();

        // Nếu người dùng nhập "true", "t", "yes", hoặc "y" thì sẽ là True.
        // Còn lại tất cả sẽ là False.
        boolean isAvailable = input.equals("true")
                || input.equals("t")
                || input.equals("y")
                || input.equals("yes");

        System.out.print("Confirm update? (Y/N): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("Update cancelled.");
            return;
        }

        System.out.println(menuService.updateMenuItem(id, name, price, category, isAvailable)
                ? "Item updated successfully."
                : "Item not found.");
    }

    // ─── Xóa món ăn ─────────────────────────────────────────────────────────────
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

    // ─── Tìm kiếm món ăn ─────────────────────────────────────────────────────────
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

    // ─── Xem tất cả món ăn
    // ─────────────────────────────────────────────────────────
private void viewAllMenuItems() {
    System.out.println();
    printLine();
    System.out.printf("| %-3s | %-30s | %-12s | %-9s| %-12s |%n",
            "ID", "Tên món", "Giá (VND)", "Trạng thái", "Danh mục");
    printLine();

    List<MenuItem> items = menuService.getAll();
    if (items.isEmpty()) {
        System.out.println("|" + center("Không có món ăn nào.", 75) + "|");
    } else {
        for (MenuItem item : items) {
            System.out.printf("| %-3d | %-30s | %,12.0f | %-9s| %-12s|%n",
                    item.getItemId(),
                    truncate(item.getName(), 30),
                    item.getPrice(),
                    item.isAvailable() ? "Còn bán" : "Hết món",
                    item.getCategory().getName());
        }
    }
    printLine();
}

private void printLine() {
    System.out.println("+-----+--------------------------------+--------------+-----------+--------------+");
}

private String truncate(String s, int maxLen) {
    return s.length() > maxLen ? s.substring(0, maxLen - 3) + "..." : s;
}

private String center(String text, int width) {
    int pad = (width - text.length()) / 2;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < pad; i++) sb.append(" ");
    sb.append(text);
    while (sb.length() < width) sb.append(" ");
    return sb.toString();
}

    // ─── Kiểm tra tồn kho
    // ─────────────────────────────────────────────────────────
private void checkStock() {
    System.out.println();
    System.out.println("+-----+--------------------------------+----------+----------+--------+----------------+");
    System.out.printf("| %-3s | %-30s | %-8s | %-8s| %-6s | %-14s |%n",
            "ID", "Tên món", "Số lượng", "Tối thiểu", "Đ.vị", "Trạng thái");
    System.out.println("+-----+--------------------------------+----------+----------+--------+----------------+");

    List<InventoryItem> inventory = inventoryService.getAll();
    if (inventory.isEmpty()) {
        System.out.println("Không có dữ liệu tồn kho.");
        return;
    }
    for (InventoryItem i : inventory) {
        String status = i.isOutOfStock() ? "HẾT HÀNG"
                : i.isLowStock() ? "SẮP HẾT"
                : "Bình thường";
        System.out.printf("| %-3d | %-30s | %-8d | %-8d | %-6s | %-14s |%n",
                i.getInventoryId(),
                truncate(i.getMenuItem().getName(), 30),
                i.getQuantity(),
                i.getMinQuantity(),
                i.getUnit(),
                status);
    }
    System.out.println("+-----+--------------------------------+----------+----------+--------+----------------+");
}

    // ─── Nhập thêm hàng tồn kho
    // ─────────────────────────────────────────────────────
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