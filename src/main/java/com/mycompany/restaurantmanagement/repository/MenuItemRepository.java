package com.mycompany.restaurantmanagement.repository;

import com.restaurant.model.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemRepository {

    private final List<MenuItem> menuItems = new ArrayList<>();
    private int nextId = 1; // Biến để theo dõi ID tự động tăng

    public int nextId() {
        return nextId++;
    }
    // Phương thức để thêm một món ăn mới
    public void save(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
    // Phương thức để lấy tất cả các món ăn
    public List<MenuItem> findAll() {
        return menuItems;
    }
    // Phương thức để tìm món ăn theo ID
    public Optional<MenuItem> findById(int id) {
        return menuItems.stream()
                .filter(menuItem -> menuItem.getItemId() == id)
                .findFirst();
    }
    // Phương thức để tìm món ăn theo danh mục
    public List<MenuItem> findByCategory(Category category) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getCategory() == category) {
                result.add(menuItem);
            }
        }
        return result;
    }
    // Phương thức để xóa món ăn theo ID
    public boolean deleteById(int id) {
        return menuItems.removeIf(menuItem -> menuItem.getItemId() == id);
    }
}
