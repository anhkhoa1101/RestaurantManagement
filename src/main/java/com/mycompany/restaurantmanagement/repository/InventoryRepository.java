package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryRepository {
    // ─── Đường dẫn file lưu trữ dữ liệu ─────────────────────────────────────────
    private static final String FILE_PATH = "data/inventory.txt";

    private final List<InventoryItem> items = new ArrayList<InventoryItem>();
    private final MenuItemRepository menuItemRepo;
    private int nextId = 1;
    // ─── Constructor ─────────────────────────────────────────────────────────
    public InventoryRepository(MenuItemRepository menuItemRepo) {
        this.menuItemRepo = menuItemRepo;
        loadFromFile();
    }

    public int nextId() {
        return nextId++;
    }

    public void save(InventoryItem item) {
        items.add(item);
        saveToFile();
    }
    // tìm tất cả các mặt hàng trong kho
    public List<InventoryItem> findAll() {
        return new ArrayList<InventoryItem>(items);
    }
    // tìm mặt hàng trong kho theo ID
    public Optional<InventoryItem> findById(int id) {
        for (InventoryItem item : items) {
            if (item.getInventoryId() == id)
                return Optional.of(item);
        }
        return Optional.empty();
    }
    // tìm mặt hàng trong kho theo ID của MenuItem
    public Optional<InventoryItem> findByMenuItemId(int menuItemId) {
        for (InventoryItem item : items) {
            if (item.getMenuItem() != null && item.getMenuItem().getItemId() == menuItemId) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
    // xóa mặt hàng trong kho theo ID
    public boolean deleteById(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getInventoryId() == id) {
                items.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    // cập nhật dữ liệu trong kho
    public void update() {
        saveToFile();
    }

    // ─── Đọc từ file ─────────────────────────────────────────────────────────

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            int maxId = 0;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split("\\|");
                if (parts.length < 5)
                    continue;

                int id = Integer.parseInt(parts[0].trim());
                int menuItemId = Integer.parseInt(parts[1].trim());
                int quantity = Integer.parseInt(parts[2].trim());
                int minQuantity = Integer.parseInt(parts[3].trim());
                String unit = parts[4].trim();

                MenuItem menuItem = menuItemRepo.findById(menuItemId).orElse(null);

                InventoryItem item = new InventoryItem(id, menuItem, quantity, minQuantity, unit);
                items.add(item);

                if (id > maxId)
                    maxId = id;
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            System.out.println("Error reading inventory.txt: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing inventory.txt: " + e.getMessage());
                }
            }
        }
    }

    // ─── Ghi ra file ─────────────────────────────────────────────────────────

    private void saveToFile() {
        new File("data").mkdirs();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(FILE_PATH, false));
            for (InventoryItem i : items) {
                int menuItemId = i.getMenuItem() != null ? i.getMenuItem().getItemId() : -1;
                writer.println(i.getInventoryId() + "|" + menuItemId + "|"
                        + i.getQuantity() + "|" + i.getMinQuantity() + "|" + i.getUnit());
            }
        } catch (IOException e) {
            System.out.println("Error writing to inventory.txt: " + e.getMessage());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
