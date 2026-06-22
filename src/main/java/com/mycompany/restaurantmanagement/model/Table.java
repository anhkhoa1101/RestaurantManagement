package com.mycompany.restaurantmanagement.model;

/**
 * [Member 3] Model - Bàn ăn trong nhà hàng.
 * Một bàn chỉ có 2 trạng thái: Trống (false) hoặc Đang có khách (true).
 */
public class Table {

    private int tableId;
    private String tableName;
    private boolean isOccupied;

    public Table(int tableId, String tableName) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.isOccupied = false; // bàn mới tạo luôn ở trạng thái Trống
    }

    public int getTableId() {
        return tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    // Bàn còn dùng được (để tạo đơn mới) khi nó KHÔNG đang có khách
    public boolean checkAvailability() {
        return !isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    @Override
    public String toString() {
        return "Bàn #" + tableId + " - " + tableName + " - "
                + (isOccupied ? "Đang có khách" : "Trống");
    }
}