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

    // tạo mới một món ăn
    public MenuItem addItem(String name, String description, double price, Category category) {
        return addMenuItem(name, description, price, category);
    }

    // Cập nhật thông tin món ăn
    public boolean updateMenuItem(int id, String name, double price) {
        Optional<MenuItem> foundMenuItem = repository.findById(id);
        if (!foundMenuItem.isPresent()) return false;
        MenuItem menuItem = foundMenuItem.get();
        menuItem.setName(name);
        menuItem.setPrice(price);
        repository.update();
        return true;
    }

    // Cập nhật thông tin món ăn
    public boolean updateItem(int id, String name, double price) {
        return updateMenuItem(id, name, price);
    }

    // Xóa một món ăn
    public boolean deleteMenuItem(int id) {
        return repository.deleteById(id);
    }

    // Xóa một món ăn
    public boolean deleteItem(int id) {
        return deleteMenuItem(id);
    }

    // Lấy tất cả món ăn
    public List<MenuItem> getAllMenuItems() {
        return repository.findAll();
    }

    // Lấy tất cả món ăn
    public List<MenuItem> getAllItems() {
        return getAllMenuItems();
    }

    // Tìm kiếm món ăn theo từ khóa
    public List<MenuItem> searchMenuItemsByName(String keyword) {
        return repository.findByName(keyword);
    }

    // Tìm kiếm món ăn theo từ khóa
    public List<MenuItem> searchItems(String keyword) {
        return searchMenuItemsByName(keyword);
    }

    // Lấy thông tin một món ăn theo ID
    public Optional<MenuItem> getMenuItemById(int id) {
        return repository.findById(id);
    }
}