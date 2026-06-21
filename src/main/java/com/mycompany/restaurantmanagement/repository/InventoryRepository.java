package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryRepository {
 
    private static final String FILE_PATH = "data/inventory.txt";
 
    private final List<InventoryItem> items = new ArrayList<>();
    private final MenuItemRepository  menuItemRepo; // Dùng để tra cứu MenuItem khi load
    private int nextId = 1;
 
    public InventoryRepository(MenuItemRepository menuItemRepo) {
        this.menuItemRepo = menuItemRepo;
        loadFromFile();
    }
 
    public int nextId()                  { return nextId++; }
 
    // Lưu vào danh sách và ghi ngay ra file
    public void save(InventoryItem item) {
        items.add(item);
        saveToFile();
    }
 
    public List<InventoryItem> findAll() { return new ArrayList<>(items); }
 
    public Optional<InventoryItem> findById(int id) {
        return items.stream().filter(i -> i.getInventoryId() == id).findFirst();
    }
 
    public Optional<InventoryItem> findByMenuItemId(int menuItemId) {
        return items.stream()
            .filter(i -> i.getMenuItem() != null
                      && i.getMenuItem().getItemId() == menuItemId)
            .findFirst();
    }
 
    public boolean deleteById(int id) {
        boolean removed = items.removeIf(i -> i.getInventoryId() == id);
        if (removed) saveToFile();
        return removed;
    }
 
    // Cập nhật thông tin món ăn và ghi lại file
    public void update() {
        saveToFile();
    }
 
    // ─── Đọc từ file ─────────────────────────────────────────────────────────
 
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
 
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            int maxId = 0;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;
 
                int    id          = Integer.parseInt(parts[0].trim());
                int    menuItemId  = Integer.parseInt(parts[1].trim());
                int    quantity    = Integer.parseInt(parts[2].trim());
                int    minQuantity = Integer.parseInt(parts[3].trim());
                String unit        = parts[4].trim();
 
                // Tra cứu MenuItem từ menuItemId đã lưu
                MenuItem menuItem = menuItemRepo.findById(menuItemId).orElse(null);
 
                InventoryItem item = new InventoryItem(id, menuItem, quantity, minQuantity, unit);
                items.add(item);
 
                if (id > maxId) maxId = id;
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            System.out.println("Lỗi đọc file inventory.txt: " + e.getMessage());
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) { /* bỏ qua */ }
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
            System.out.println("Lỗi ghi file inventory.txt: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    }
}
