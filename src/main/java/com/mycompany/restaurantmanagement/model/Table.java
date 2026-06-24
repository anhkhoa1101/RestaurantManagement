package com.mycompany.restaurantmanagement.model;

/**
 * [Member 3] Model - Bàn ăn trong nhà hàng.
 * Một bàn chỉ có 2 trạng thái: Trống (false) hoặc Đang có khách (true).
 */
public class Table {

    // Attributes (private theo nguyên tắc đóng gói)
    private int tableId;
    private String tableName;
    private int capacity;
    private boolean isOccupied;

    // Constructor
    public Table(int tableId, String tableName, int capacity, boolean isOccupied) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.capacity = capacity;
        this.isOccupied = isOccupied;
    }

    // Getter
    public int getTableId() {
        return tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    // Kiểm tra bàn còn trống không
    public boolean checkAvailability() {
        return !isOccupied;
    }

    // Cập nhật trạng thái bàn
    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    // Hiển thị thông tin
    @Override
    public String toString() {
        return "Table{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", capacity=" + capacity +
                ", isOccupied=" + isOccupied +
                '}';
    }
}