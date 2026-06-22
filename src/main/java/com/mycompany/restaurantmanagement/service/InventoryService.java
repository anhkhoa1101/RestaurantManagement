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
    public InventoryItem addInventoryItem(MenuItem menuItem, int quantity, int minQuantity, String unit) {
        InventoryItem item = new InventoryItem(repository.nextId(), menuItem, quantity, minQuantity, unit);
        repository.save(item);
        return item;
    }

    // Nhập thêm hàng vào kho
    public boolean restock(int inventoryId, int amount) {
        Optional<InventoryItem> found = repository.findById(inventoryId);
        if (!found.isPresent())
            return false;
        found.get().addStock(amount);
        repository.update();
        return true;
    }

    // Giảm tồn kho khi bán món (dùng bởi Module 3 — Order)
    public boolean reduceStock(int menuItemId, int amount) {
        Optional<InventoryItem> found = repository.findByMenuItemId(menuItemId);
        if (!found.isPresent())
            return false;
        found.get().reduceStock(amount);
        repository.update();
        return true;
    }

    // Kiểm tra món còn hàng không (dùng bởi Module 3 trước khi gọi món)
    public boolean isInStock(int menuItemId) {
        Optional<InventoryItem> found = repository.findByMenuItemId(menuItemId);
        return found.isPresent() && !found.get().isOutOfStock();
    }

    // Lấy tất cả mặt hàng tồn kho
    public List<InventoryItem> getAllInventoryItems() {
        return repository.findAll();
    }

    // Lấy danh sách mặt hàng đang ở mức tồn kho thấp
    public List<InventoryItem> getLowStockItems() {
        List<InventoryItem> result = new ArrayList<InventoryItem>();
        for (InventoryItem item : repository.findAll()) {
            if (item.isLowStock())
                result.add(item);
        }
        return result;
    }

    // Lấy thông tin một mặt hàng tồn kho theo ID
    public Optional<InventoryItem> getById(int id) {
        return repository.findById(id);
    }
}
