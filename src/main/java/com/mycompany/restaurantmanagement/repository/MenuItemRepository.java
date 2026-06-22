package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Category;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemRepository {
 
    private static final String FILE_PATH = "data/menu_items.txt";
 
    private final List<MenuItem>    items = new ArrayList<>();
    private final CategoryRepository categoryRepo; // Dùng để tra cứu Category khi load
    private int nextId = 1;
 
    // Constructor: nhận CategoryRepository để resolve quan hệ khi đọc file
    public MenuItemRepository(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
        loadFromFile();
    }
 
    public int nextId()             { return nextId++; }
 
    // Lưu vào danh sách và ghi ngay ra file
    public void save(MenuItem item) {
        items.add(item);
        saveToFile();
    }
 
    public List<MenuItem> findAll() { return new ArrayList<>(items); }
 
    public Optional<MenuItem> findById(int id) {
        return items.stream().filter(i -> i.getItemId() == id).findFirst();
    }
 
    public List<MenuItem> findByKeyword(String keyword) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.matchesKeyword(keyword)) result.add(item);
        }
        return result;
    }
 
    // lấy tên món ăn làm keyword tìm kiếm
    public List<MenuItem> findByName(String keyword) {
        return findByKeyword(keyword);
    }
    // Xóa khỏi danh sách và ghi lại file.
    public boolean deleteById(int id) {
        boolean removed = items.removeIf(i -> i.getItemId() == id);
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
                if (parts.length < 6) continue;
 
                int    id          = Integer.parseInt(parts[0].trim());
                String name        = parts[1].trim();
                String description = parts[2].trim();
                double price       = Double.parseDouble(parts[3].trim());
                int    categoryId  = Integer.parseInt(parts[4].trim());
                boolean isAvailable= Boolean.parseBoolean(parts[5].trim());
 
                // Tra cứu Category từ categoryId đã lưu
                Category category = categoryRepo.findById(categoryId).orElse(null);
 
                MenuItem item = new MenuItem(id, name, description, price, category);
                item.setAvailable(isAvailable);
                items.add(item);
 
                if (id > maxId) maxId = id;
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            System.out.println("Lỗi đọc file menu_items.txt: " + e.getMessage());
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
            for (MenuItem i : items) {
                // Lưu categoryId thay vì toàn bộ object Category
                int categoryId = i.getCategory() != null ? i.getCategory().getId() : -1;
                writer.println(i.getItemId() + "|" + i.getName() + "|"
                    + i.getDescription() + "|" + i.getPrice() + "|"
                    + categoryId + "|" + i.isAvailable());
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file menu_items.txt: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    }
}
