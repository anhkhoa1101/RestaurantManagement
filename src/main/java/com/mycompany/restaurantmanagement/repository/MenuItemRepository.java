package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.model.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MenuItemRepository extends BaseRepository<MenuItem, Integer> {
    private final CategoryRepository categoryRepo;
    private int nextId = 1;
    
    private List<Integer> tempCategoryIds = null;

    //───Constructor──────────────────────────────────────────────────────────────
    public MenuItemRepository(CategoryRepository categoryRepo) {
        super(AppConfig.MENU_FILE_PATH);
        this.categoryRepo = categoryRepo;
        bindCategories();
        calculateNextId();
    }
    
    public int nextId() {
        return nextId++;
    }
    
    //───Find by ID──────────────────────────────────────────────────────────────
    @Override
    public MenuItem findById(Integer id) {
        if (id == null) return null;
        for (MenuItem item : data) {
            if (item != null && item.getItemId() == id.intValue()) {
                return item;
            }
        }
        return null;
    }

    //───Find by Name──────────────────────────────────────────────────────────────
    public List<MenuItem> findByName(String keyword) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : data) {
            if (item != null && item.matchesKeyword(keyword)) {
                result.add(item);
            }
        }
        return result;
    }
    
    //───Update────────────────────────────────────────────────────────────────
    public void update() {
        saveToFile();
    }
    
    //───Calculate Next ID──────────────────────────────────────────────────────
    private void calculateNextId() {
        int maxId = 0;
        for (MenuItem item : data) {
            if (item != null && item.getItemId() > maxId) {
                maxId = item.getItemId();
            }
        }
        this.nextId = maxId + 1;
    }
    
    //───Bind Categories──────────────────────────────────────────────────────────
    private void bindCategories() {
        if (categoryRepo == null || tempCategoryIds == null) return;

        for (int i = 0; i < data.size(); i++) {
            if (i < tempCategoryIds.size() && data.get(i) != null) {
                int categoryId = tempCategoryIds.get(i);
                Category realCategory = categoryRepo.findById(categoryId);
                data.get(i).setCategory(realCategory);
            }
        }
        tempCategoryIds.clear();
    }
    
    //───Parse and ToLine────────────────────────────────────────────────────────
    @Override
    protected MenuItem parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) return null;

        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String description = parts[2].trim();
        double price = Double.parseDouble(parts[3].trim());
        int categoryId = Integer.parseInt(parts[4].trim());
        boolean isAvailable = Boolean.parseBoolean(parts[5].trim());

        if (tempCategoryIds == null) {
            tempCategoryIds = new ArrayList<>();
        }
        tempCategoryIds.add(categoryId);

        MenuItem item = new MenuItem(id, name, description, price, null);
        item.setAvailable(isAvailable);
        return item;
    }

    @Override
    protected String toLine(MenuItem i) {
        int categoryId = i.getCategory() != null ? i.getCategory().getId() : -1;
        return i.getItemId() + "|" + i.getName() + "|"
                + i.getDescription() + "|" + i.getPrice() + "|"
                + categoryId + "|" + i.isAvailable();
    }
}