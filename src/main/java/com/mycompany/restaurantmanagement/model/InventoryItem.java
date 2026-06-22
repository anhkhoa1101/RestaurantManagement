package com.mycompany.restaurantmanagement.model;

import java.util.Date;

public class InventoryItem {

    private int inventoryId;
    private MenuItem menuItem;
    private int quantity;
    private int minQuantity;
    private String unit;
    private Date lastUpdated;

    // ─── Constructor ─────────────────────────────────────────────────────────

    public InventoryItem(int inventoryId, MenuItem menuItem, int quantity, int minQuantity, String unit) {
        this.inventoryId = inventoryId;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.unit = unit;
        this.lastUpdated = new Date();
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public int getInventoryId() {
        return inventoryId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    // ─── Setters ──────────────────────────────────────────────────────────────

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // ─── Thay đổi số lượng tồn kho ───────────────────────────────────────────
    // Mọi thay đổi quantity đều đi qua 2 method này để đảm bảo validate + cập nhật
    // lastUpdated

    public void addStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount to add must be greater than 0.");
        this.quantity += amount;
        this.lastUpdated = new Date();
    }

    public void reduceStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Stock to reduce must be greater than 0.");
        if (amount > this.quantity)
            throw new IllegalStateException(
                    "Insufficient stock: need " + amount + " but only " + this.quantity + " " + unit + " available.");
        this.quantity -= amount;
        this.lastUpdated = new Date();
    }

    // ─── Kiểm tra trạng thái kho ──────────────────────────────────────────────

    public boolean isLowStock() {
        return quantity <= minQuantity && quantity > 0;
    }

    public boolean isOutOfStock() {
        return quantity == 0;
    }

    // ─── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("InventoryItem{id=%d, item='%s', qty=%d %s, min=%d, lastUpdated=%s}",
                inventoryId, menuItem != null ? menuItem.getName() : "N/A",
                quantity, unit, minQuantity, lastUpdated);
    }
}
