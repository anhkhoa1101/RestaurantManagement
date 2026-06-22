package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.model.MenuItem;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemRepository {
    // ─── Đường dẫn file lưu trữ dữ liệu ─────────────────────────────────────────
    private static final String FILE_PATH = "data/menu_items.txt";

    private final List<MenuItem> items = new ArrayList<MenuItem>();
    private final CategoryRepository categoryRepo;
    private int nextId = 1;
    // ─── Constructor ─────────────────────────────────────────────────────────
    public MenuItemRepository(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
        loadFromFile();
    }
    // ─── Lấy ID tiếp theo cho MenuItem mới ───────────────────────────────────────
    public int nextId() {
        return nextId++;
    }

    public void save(MenuItem item) {
        items.add(item);
        saveToFile();
    }
    // tìm tất cả các mặt hàng trong menu
    public List<MenuItem> findAll() {
        return new ArrayList<MenuItem>(items);
    }
    // tìm mặt hàng trong menu theo ID
    public Optional<MenuItem> findById(int id) {
        for (MenuItem item : items) {
            if (item.getItemId() == id)
                return Optional.of(item);
        }
        return Optional.empty();
    }

    // findByName dùng thống nhất — tìm theo tên và mô tả qua matchesKeyword()
    public List<MenuItem> findByName(String keyword) {
        List<MenuItem> result = new ArrayList<MenuItem>();
        for (MenuItem item : items) {
            if (item.matchesKeyword(keyword))
                result.add(item);
        }
        return result;
    }
    // xóa mặt hàng trong menu theo ID
    public boolean deleteById(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId() == id) {
                items.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    // cập nhật dữ liệu trong menu
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
                if (parts.length < 6)
                    continue;

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String description = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());
                int categoryId = Integer.parseInt(parts[4].trim());
                boolean isAvailable = Boolean.parseBoolean(parts[5].trim());

                Category category = categoryRepo.findById(categoryId).orElse(null);
                MenuItem item = new MenuItem(id, name, description, price, category);
                item.setAvailable(isAvailable);
                items.add(item);
                if (id > maxId)
                    maxId = id;
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            System.out.println("Error reading menu_items.txt: " + e.getMessage());
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing menu_items.txt: " + e.getMessage());
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
                int categoryId = i.getCategory() != null ? i.getCategory().getId() : -1;
                writer.println(i.getItemId() + "|" + i.getName() + "|"
                        + i.getDescription() + "|" + i.getPrice() + "|"
                        + categoryId + "|" + i.isAvailable());
            }
        } catch (IOException e) {
            System.out.println("Error writing to menu_items.txt: " + e.getMessage());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
