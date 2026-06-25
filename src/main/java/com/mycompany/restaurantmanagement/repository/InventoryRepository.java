package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository extends BaseRepository<InventoryItem, Integer> {
    private static final String FILE_PATH = "data/inventory.txt";
    private final MenuItemRepository menuItemRepo;
    private int nextId = 1;
    //───Constructor──────────────────────────────────────────────────────────────
    public InventoryRepository(MenuItemRepository menuItemRepo) {
        super(FILE_PATH);
        this.menuItemRepo = menuItemRepo;
        calculateNextId();
    }
    //───Find by ID──────────────────────────────────────────────────────────────
    public int nextId() {
        return nextId++;
    }
    // tim kiếm theo id của InventoryItem
    @Override
    public InventoryItem findById(Integer id) {
        for (InventoryItem item : data) {
            if (item.getInventoryId() == id) {
                return item;
            }
        }
        return null;
    }

    // tim kiếm theo id của MenuItem
    public InventoryItem findByMenuItemId(int menuItemId) {
        for (InventoryItem item : data) {
            if (item.getMenuItem() != null && item.getMenuItem().getItemId() == menuItemId) {
                return item;
            }
        }
        return null;
    }
    //───Update────────────────────────────────────────────────────────────────
    public void update() {
        saveToFile();
    }
    //───Calculate Next ID──────────────────────────────────────────────────────
    private void calculateNextId() {
        int maxId = 0;
        for (InventoryItem item : data) {
            if (item.getInventoryId() > maxId) {
                maxId = item.getInventoryId();
            }
        }
        this.nextId = maxId + 1;
    }
    //───Parse and ToLine────────────────────────────────────────────────────────
    // liên kết với BaseRepository để parse dữ liệu từ file và lưu dữ liệu vào file
    // parseLine: Chuyển đổi một dòng dữ liệu từ file thành đối tượng InventoryItem
    // toLine: Chuyển đổi một đối tượng InventoryItem thành một dòng dữ liệu để lưu vào file
    @Override
    protected InventoryItem parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 5) return null;

        int id = Integer.parseInt(parts[0].trim());
        int menuItemId = Integer.parseInt(parts[1].trim());
        int quantity = Integer.parseInt(parts[2].trim());
        int minQuantity = Integer.parseInt(parts[3].trim());
        String unit = parts[4].trim();

        MenuItem menuItem = menuItemRepo.findById(menuItemId);

        return new InventoryItem(id, menuItem, quantity, minQuantity, unit);
    }
    
    @Override
    protected String toLine(InventoryItem i) {
        int menuItemId = i.getMenuItem() != null ? i.getMenuItem().getItemId() : -1;
        return i.getInventoryId() + "|" + menuItemId + "|"
                + i.getQuantity() + "|" + i.getMinQuantity() + "|" + i.getUnit();
    }
}