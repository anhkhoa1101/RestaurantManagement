package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.Category;

public class CategoryRepository extends BaseRepository<Category, Integer> {

    private int nextId = 1;

    //───Constructor──────────────────────────────────────────────────────────────
    public CategoryRepository() {
        super(AppConfig.CATEGORIES_FILE_PATH);
        calculateNextId();
    }

    public int nextId() {
        return nextId++;
    }

    //───Find by ID──────────────────────────────────────────────────────────────
    @Override
    public Category findById(Integer id) {
        if (id == null) return null;
        // [FIX] Dùng id.intValue() thay vì == để tránh lỗi so sánh Integer với int
        // khi id > 127, Java không cache Integer nên == có thể trả false
        for (Category c : data) {
            if (c != null && c.getId() == id.intValue()) {
                return c;
            }
        }
        return null;
    }

    //───Delete by ID──────────────────────────────────────────────────────────
    public boolean deleteById(int id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) != null && data.get(i).getId() == id) {
                data.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    //───Calculate Next ID──────────────────────────────────────────────────────
    private void calculateNextId() {
        int maxId = 0;
        for (Category c : data) {
            if (c != null && c.getId() > maxId) {
                maxId = c.getId();
            }
        }
        this.nextId = maxId + 1;
    }

    //───Parse and ToLine────────────────────────────────────────────────────────
    // parseLine: đọc một dòng từ file → tạo object Category
    // toLine: chuyển object Category → một dòng để ghi file
    @Override
    protected Category parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 4) return null;

        int id           = Integer.parseInt(parts[0].trim());
        String name      = parts[1].trim();
        String desc      = parts[2].trim();
        boolean isActive = Boolean.parseBoolean(parts[3].trim());

        Category c = new Category(id, name, desc);
        c.setActive(isActive);
        return c;
    }

    @Override
    protected String toLine(Category c) {
        return c.getId() + "|" + c.getName() + "|" + c.getDescription() + "|" + c.isActive();
    }
}