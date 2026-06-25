package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;

public class InventoryRepository extends BaseRepository<InventoryItem, Integer> {

    private final MenuItemRepository menuItemRepo;
    private int nextId = 1;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public InventoryRepository(MenuItemRepository menuItemRepo) {

        super(AppConfig.INVENTORY_FILE_PATH);       
        this.menuItemRepo = menuItemRepo;
        bindMenuItems();
        calculateNextId();
    }

    public int nextId() {
        return nextId++;
    }

    // ─── Find by ID ──────────────────────────────────────────────────────────
@Override
public InventoryItem findById(Integer id) {
    if (id == null) {
        return null;
    }
    
    for (InventoryItem item : data) {
        if (item == null) {
            continue;
        }
        
        if (item.getInventoryId() == id.intValue()) {
            return item;
        }
    }
    return null;
}

    // ─── Find by MenuItem ID ──────────────────────────────────────────────────
    public InventoryItem findByMenuItemId(int menuItemId) {
        for (InventoryItem item : data) {
            if (item.getMenuItem() != null && item.getMenuItem().getItemId() == menuItemId) {
                return item;
            }
        }
        return null;
    }

    // ─── Update ──────────────────────────────────────────────────────────────
    public void update() {
        saveToFile();
    }

    // ─── Calculate Next ID ────────────────────────────────────────────────────
    private void calculateNextId() {
        int maxId = 0;
        for (InventoryItem item : data) {
            if (item.getInventoryId() > maxId) {
                maxId = item.getInventoryId();
            }
        }
        this.nextId = maxId + 1;
    }

    // ─── Bind Menu Items (Đã sửa để hết lỗi Null) ─────────────────────────────
    private void bindMenuItems() {
        if (menuItemRepo == null) return;

        for (InventoryItem item : data) {
            if (item.getMenuItem() != null) {
                int savedId = item.getMenuItem().getItemId();
                
                MenuItem realMenuItem = menuItemRepo.findById(savedId);
                
                item.setMenuItem(realMenuItem);
            }
        }
    }

    // ─── Parse and ToLine ────────────────────────────────────────────────────
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

        MenuItem tempFakeMenu = new MenuItem(menuItemId, "", "", 0.0, null);

        return new InventoryItem(id, tempFakeMenu, quantity, minQuantity, unit);
    }

    @Override
    protected String toLine(InventoryItem i) {
        int menuItemId = i.getMenuItem() != null ? i.getMenuItem().getItemId() : -1;
        return i.getInventoryId() + "|" + menuItemId + "|"
                + i.getQuantity() + "|" + i.getMinQuantity() + "|" + i.getUnit();
    }
}