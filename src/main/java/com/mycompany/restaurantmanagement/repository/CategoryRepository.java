package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Category;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository {
    // ─── Đường dẫn file lưu trữ dữ liệu ─────────────────────────────────────────
    private static final String FILE_PATH = "data/categories.txt";

    private final List<Category> categories = new ArrayList<Category>();
    private int nextId = 1;

    // ─── Constructor ─────────────────────────────────────────────────────────
    public CategoryRepository() {
        loadFromFile();
    }

    public int nextId() {
        return nextId++;
    }

    public void save(Category category) {
        categories.add(category);
        saveToFile();
    }

    // ─── Tìm tất cả các danh mục ────────────────────────────────────────────────
    public List<Category> findAll() {
        return new ArrayList<Category>(categories);
    }

    // ─── Tìm danh mục theo ID ──────────────────────────────────────────────────
    public Optional<Category> findById(int id) {
        for (Category c : categories) {
            if (c.getId() == id)
                return Optional.of(c);
        }
        return Optional.empty();
    }
    // ─── Xóa danh mục theo ID ──────────────────────────────────────────────────
    public boolean deleteById(int id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == id) {
                categories.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
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
                if (parts.length < 4)
                    continue;

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String desc = parts[2].trim();
                boolean isActive = Boolean.parseBoolean(parts[3].trim());

                Category c = new Category(id, name, desc);
                c.setActive(isActive);
                categories.add(c);
                if (id > maxId)
                    maxId = id;
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            System.out.println("Error reading categories.txt: " + e.getMessage());
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing categories.txt: " + e.getMessage());
                }
        }
    }

    // ─── Ghi ra file ─────────────────────────────────────────────────────────

    private void saveToFile() {
        new File("data").mkdirs();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(FILE_PATH, false));
            for (Category c : categories) {
                writer.println(c.getId() + "|" + c.getName() + "|" + c.getDescription() + "|" + c.isActive());
            }
        } catch (IOException e) {
            System.out.println("Error writing to categories.txt: " + e.getMessage());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
