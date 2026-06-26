package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.InventoryItem;
import com.mycompany.restaurantmanagement.model.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository extends BaseRepository<InventoryItem, Integer> {

    private final MenuItemRepository menuItemRepo;
    private int nextId = 1;

    private List<Integer> tempMenuItemIds = null;

    //───Constructor──────────────────────────────────────────────────────────────
    public InventoryRepository(MenuItemRepository menuItemRepo) {
        super(AppConfig.INVENTORY_FILE_PATH);
        this.menuItemRepo = menuItemRepo;  
        bindMenuItems();
        calculateNextId();
    }

    public int nextId() {
        return nextId++;
    }

    //───Find by ID──────────────────────────────────────────────────────────────
    @Override
    public InventoryItem findById(Integer id) {
        if (id == null) return null;
        for (InventoryItem item : data) {
            if (item != null && item.getInventoryId() == id.intValue()) {
                return item;
            }
        }
        return null;
    }

    //───Find by MenuItem ID──────────────────────────────────────────────────────
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
            if (item != null && item.getInventoryId() > maxId) {
                maxId = item.getInventoryId();
            }
        }
        this.nextId = maxId + 1;
    }

    //───Bind Menu Items ────────────────────────────────────────────────────────
    private void bindMenuItems() {
        if (menuItemRepo == null || tempMenuItemIds == null) return;

        for (int i = 0; i < data.size(); i++) {
            if (i < tempMenuItemIds.size() && data.get(i) != null) {
                int menuItemId = tempMenuItemIds.get(i);
                MenuItem realMenuItem = menuItemRepo.findById(menuItemId);
                data.get(i).setMenuItem(realMenuItem);
            }
        }
        tempMenuItemIds.clear();
    }

    //───Parse and ToLine────────────────────────────────────────────────────────
    @Override
    protected InventoryItem parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 5) return null;

        int id = Integer.parseInt(parts[0].trim());
        int menuItemId = Integer.parseInt(parts[1].trim());
        int quantity = Integer.parseInt(parts[2].trim());
        int minQuantity = Integer.parseInt(parts[3].trim());
        String unit = parts[4].trim();
        if (tempMenuItemIds == null) {
            tempMenuItemIds = new ArrayList<>();
        }
        tempMenuItemIds.add(menuItemId); 
        return new InventoryItem(id, null, quantity, minQuantity, unit);
    }

    @Override
    protected String toLine(InventoryItem i) {
        int menuItemId = i.getMenuItem() != null ? i.getMenuItem().getItemId() : -1;
        return i.getInventoryId() + "|" + menuItemId + "|"
                + i.getQuantity() + "|" + i.getMinQuantity() + "|" + i.getUnit();
    }
}