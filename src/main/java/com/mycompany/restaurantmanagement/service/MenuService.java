package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.repository.MenuItemRepository;
import java.util.List;
import java.util.Optional;

public class MenuService {

    private final MenuItemRepository repository;

    public MenuService(MenuItemRepository repository) {
        this.repository = repository;
    }

    // Tạo mới một món ăn
    public MenuItem addMenuItem(String name, String description, double price, Category category) {
        MenuItem menuItem = new MenuItem(repository.nextId(), name, description, price, category);
        repository.save(menuItem);
        return menuItem;
    }

    // Cập nhật thông tin món ăn
    public boolean updateMenuItem(int id, String name, double price) {
        Optional<MenuItem> foundMenuItem = repository.findById(id);
        if(!foundMenuItem.isPresent())
        return false;
        MenuItem menuItem = foundMenuItem.get();
        menuItem.setName(name);
        menuItem.setPrice(price);
        repository.update();
        return true;
    }

    // Xóa một món ăn
    public boolean deleteMenuItem(int id) {
        return repository.deleteById(id);
    }
    // Lấy tất cả món ăn
    public List<MenuItem> getAllMenuItems() {
        return repository.findAll();
    }
    // Lấy thông tin một món ăn theo từ khóa
    public List<MenuItem> searchMenuItemsByName(String keyword) {
        return repository.findByName(keyword);
    }
    // Lấy thông tin một món ăn theo ID
    public Optional<MenuItem> getMenuItemById(int id) {
        return repository.findById(id);
    }
}
