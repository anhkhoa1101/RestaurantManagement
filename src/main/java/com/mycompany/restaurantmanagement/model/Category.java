package com.mycompany.restaurantmanagement.model;

public class Category {

    private int categoryId;
    private String name;
    private String description;
    private boolean isActive;

    // ─── Constructor ─────────────────────────────────────────────────────────

    public Category(int categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.isActive = true;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public int getId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    // ─── Setters ──────────────────────────────────────────────────────────────

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Category name must not be empty.");
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // ─── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("Category{id=%d, name='%s', active=%s}", categoryId, name, isActive);
    }
}
