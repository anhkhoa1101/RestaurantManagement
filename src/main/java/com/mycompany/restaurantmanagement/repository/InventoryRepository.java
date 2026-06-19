package com.mycompany.restaurantmanagement.repository;

import com.restaurant.model.InventoryItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryRepository {

    private final List<InventoryItem> inventoryItems = new ArrayList<>();
    private int nextId = 1; // Biến để theo dõi ID tự động tăng

    // Phương thức để tạo ID tự động tăng
    public int nextId() {
        return nextId++;
    }

    // Phương thức để thêm một món ăn mới vào kho
    public void save(InventoryItem inventoryItem) {
        inventoryItems.add(inventoryItem);
    }

    // Phương thức để lấy tất cả các món ăn trong kho
    public List<InventoryItem> findAll() {
        return inventoryItems;
    }

    // Phương thức để tìm món ăn trong kho theo ID
    public Optional<InventoryItem> findById(int id) {
        return inventoryItems.stream()
                .filter(inventoryItem -> inventoryItem.getInventoryId() == id)
                .findFirst();
    }

    // Phương thức để xóa món ăn trong kho theo ID
    public boolean deleteById(int id) {
        return inventoryItems.removeIf(inventoryItem -> inventoryItem.getInventoryId() == id);
    }

}
