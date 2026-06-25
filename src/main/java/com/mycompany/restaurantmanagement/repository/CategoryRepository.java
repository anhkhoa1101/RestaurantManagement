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
        for (Category c : data) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    //───Update────────────────────────────────────────────────────────────────
    private void calculateNextId() {
        int maxId = 0;
        for (Category c : data) {
            if (c.getId() > maxId) {
                maxId = c.getId();
            }
        }
        this.nextId = maxId + 1;
    }
    //───Parse and ToLine────────────────────────────────────────────────────────
    // liên kết với BaseRepository để parse dữ liệu từ file và lưu dữ liệu vào file
    // parseLine: Chuyển đổi một dòng dữ liệu từ file thành đối tượng Category
    // toLine: Chuyển đổi một đối tượng Category thành một dòng dữ liệu để lưu vào file
    @Override
    protected Category parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 4)
            return null;

        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String desc = parts[2].trim();
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