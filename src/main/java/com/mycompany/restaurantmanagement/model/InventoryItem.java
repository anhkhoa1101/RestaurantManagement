/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.model;

/**
 *
 * @author khoa0
 */
import java.util.Date;

public class InventoryItem {
    private int inventoryId;
    private MenuItem menuItem;
    private int quantity;
    private int minQuantity;
    private String unit;
    private Date lastUpdated;

    public InventoryItem(int inventoryId, MenuItem menuItem, int quantity, int minQuantity, String unit){
        this.inventoryId = inventoryId;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.lastUpdated = new Date();
    }

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

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity must not be negative.");
        this.quantity = quantity;
        this.lastUpdated = new Date();
    }

    public void reduceStock(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Stock to reduce must be greater than 0.");
        if (quantity > this.quantity)
            throw new IllegalStateException(
                    "Insufficient stock: need " + quantity + " but only " + quantity + " " + unit + " available.");
        this.quantity = quantity;
        this.lastUpdated = new Date();
    }
    public boolean isLowStock(){
        return  quantity <= minQuantity && quantity > 0;
    }
    public boolean isOutOfStock(){
        return quantity == 0;
    }
    @Override
    public String toString(){
        return String.format("InventoryItem{id=%d, item='%s', qty=%d %s, min=%d, lastUpdated=%s}", inventoryId, menuItem != null ? menuItem.getName() : "N/A", quantity, unit, minQuantity, lastUpdated);
    }
}

