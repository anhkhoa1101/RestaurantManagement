package com.mycompany.restaurantmanagement.model;

public class MenuItem {

    private int itemId;
    private String name;
    private String description;
    private double price;
    private Category category;
    private boolean isAvailable;

    // ─── Constructor ─────────────────────────────────────────────────────────

    public MenuItem(int itemId, String name, String description, double price, Category category) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isAvailable = true;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // ─── Setters ──────────────────────────────────────────────────────────────

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Item name must not be empty.");
        this.name = name;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price must not be negative.");
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    // ─── Tìm kiếm theo từ khóa ────────────────────────────────────────────────

    public boolean matchesKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty())
            return true;
        String kw = keyword.trim().toLowerCase();
        return name.toLowerCase().contains(kw)
                || (description != null && description.toLowerCase().contains(kw));
    }

    // ─── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("MenuItem{id=%d, name='%s', price=%.0f VND, available=%s, category='%s'}",
                itemId, name, price, isAvailable,
                category != null ? category.getName() : "N/A");
    }
}
