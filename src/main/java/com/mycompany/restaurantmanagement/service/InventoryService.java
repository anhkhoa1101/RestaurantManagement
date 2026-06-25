package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.repository.InventoryRepository;
import java.util.ArrayList;
import java.util.List;

public class InventoryService extends BaseService<InventoryItem, Integer> {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository repository) {
        super(repository);
        this.inventoryRepository = repository;
    }

    // Tạo mới một mặt hàng trong kho, tự động tạo ID mới và lưu vào file
    public InventoryItem addInventoryItem(MenuItem menuItem, int quantity, int minQuantity, String unit) {
        InventoryItem item = new InventoryItem(inventoryRepository.nextId(), menuItem, quantity, minQuantity, unit);
        add(item);
        return item;
    }

    // Nhập thêm số lượng vào kho, tự động cập nhật và lưu vào file
    public boolean restock(Integer inventoryId, int amount) {
        InventoryItem found = getById(inventoryId);
        if (found == null) {
            return false;
        }
        found.addStock(amount);
        update(found); // Gọi hàm update của BaseService để lưu trạng thái mới
        return true;
    }

    // tự động giảm số lượng trong kho khi bán món ăn, tự động cập nhật và lưu vào file
    public boolean reduceStock(Integer menuItemId, int amount) {
        InventoryItem found = inventoryRepository.findByMenuItemId(menuItemId);
        if (found == null) {
            return false;
        }
        found.reduceStock(amount);
        update(found);
        return true;
    }

    // kiểm tra xem món ăn có còn trong kho hay không, dựa trên số lượng hiện tại
    public boolean isInStock(Integer menuItemId) {
        InventoryItem found = inventoryRepository.findByMenuItemId(menuItemId);
        return found != null && !found.isOutOfStock();
    }

    // Tìm kiếm các mặt hàng trong kho có số lượng thấp hơn mức tối thiểu, trả về danh sách các mặt hàng đó
    public List<InventoryItem> getLowStockItems() {
        List<InventoryItem> result = new ArrayList<>();
        // getAll() lấy từ BaseService, trả về bản sao danh sách an toàn
        for (InventoryItem item : getAll()) { 
            if (item.isLowStock()) {
                result.add(item);
            }
        }
        return result;
    }
}