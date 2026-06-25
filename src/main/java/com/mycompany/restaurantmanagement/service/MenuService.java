package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.repository.MenuItemRepository;
import java.util.List;

public class MenuService extends BaseService<MenuItem, Integer> {

    private final MenuItemRepository menuItemRepository;

    public MenuService(MenuItemRepository repository) {
        super(repository);
        this.menuItemRepository = repository;
    }

    // Tạo mới một món ăn
    public MenuItem addMenuItem(String name, String description, double price, Category category) {
        MenuItem menuItem = new MenuItem(menuItemRepository.nextId(), name, description, price, category);
        add(menuItem); 
        return menuItem;
    }

    // Cập nhật tên và giá món ăn
    public boolean updateMenuItem(int id, String name, double price) {
        MenuItem found = getById(id); 
        if (found == null) {
            return false;
        }
        found.setName(name);
        found.setPrice(price);
        update(found); 
        return true;
    }

    // Xóa một món ăn
    public boolean deleteMenuItem(int id) {
        MenuItem found = getById(id);
        if (found == null) {
            return false;
        }
        remove(id); 
        return true;
    }

    // Lấy tất cả món ăn
    public List<MenuItem> getAllMenuItems() {
        return getAll(); 
    }

    // Tìm kiếm món ăn theo từ khóa
    public List<MenuItem> searchMenuItems(String keyword) {
        return menuItemRepository.findByName(keyword);
    }
}