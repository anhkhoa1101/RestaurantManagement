package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.model.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MenuItemRepository extends BaseRepository<MenuItem, Integer> {
    private final CategoryRepository categoryRepo;
    private int nextId = 1;
    
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
    // tim kiếm theo id của MenuItem
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
    
    //───Bind Categories ────────────────────────────────────────────────────────
    private void bindCategories() {
        if (categoryRepo == null) return;

        for (MenuItem item : data) {
            if (item != null && item.getCategory() != null) {
                // Lấy cái ID tạm đang lưu trong Object Category giả lập ra
                int savedCategoryId = item.getCategory().getId();
                
                // Tra cứu thực thể Category thực sự từ danh mục dữ liệu
                Category realCategory = categoryRepo.findById(savedCategoryId);
                
                // Gán đè thực thể chuẩn OOP hoàn chỉnh vào món ăn
                item.setCategory(realCategory);
            }
        }
    }
    
    //───Parse and ToLine────────────────────────────────────────────────────────
    // liên kết với BaseRepository để parse dữ liệu từ file và lưu dữ liệu vào file 
    // parseLine: Chuyển đổi một dòng dữ liệu từ file thành đối tượng MenuItem
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

        Category tempFakeCategory = new Category(categoryId, "", "");
        
        MenuItem item = new MenuItem(id, name, description, price, tempFakeCategory);
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