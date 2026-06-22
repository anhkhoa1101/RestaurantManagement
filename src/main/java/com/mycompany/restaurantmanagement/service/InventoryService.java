package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.repository.InventoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }
    // Tạo mới một mặt hàng tồn kho
    public InventoryItem addInventoryItem(MenuItem menuItem, int quantity, int minimumQuantity, String unit) {
        InventoryItem inventoryItem = new InventoryItem(repository.nextId(), menuItem, quantity, minimumQuantity, unit);
        repository.save(inventoryItem);
        return inventoryItem;
    }
    // Cập nhật thông tin mặt hàng tồn kho
    public boolean restock(int inventoryId, int quantity) {
        Optional<InventoryItem> foundItem = repository.findById(inventoryId);
        if (!foundItem.isPresent()) return false;
        foundItem.get().addStock(quantity);
        repository.update();
        return true;
    }
    // Giảm số lượng tồn kho khi bán món ăn
    public boolean reduceStock(int menuItemId, int quantity) {
        Optional<InventoryItem> foundItem = repository.findByMenuItemId(menuItemId);
        if (!foundItem.isPresent())
        return false;
        foundItem.get().reduceStock(quantity);
        repository.update();
        return true;
    }
    // Kiểm tra tình trạng tồn kho của một món ăn
    public boolean checkStock(int menuItemId) {
        Optional<InventoryItem> foundItem = repository.findByMenuItemId(menuItemId);
        if (!foundItem.isPresent())
        return false;
        return !foundItem.get().isOutOfStock();
    }
    // Lấy tất cả mặt hàng tồn kho
    public List<InventoryItem> getAllInventoryItems() {
        return repository.findAll();
    }
    // Lấy danh sách mặt hàng tồn kho đang ở mức thấp
    public List<InventoryItem> getLowStockItems() {
        List<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : repository.findAll()) {
            if (item.isLowStock()) {
                result.add(item);
            }
        }
        return result;
    }
    // Lấy thông tin một mặt hàng tồn kho theo ID
    public Optional<InventoryItem> getById(int id) {
        return repository.findById(id);
    }
}
