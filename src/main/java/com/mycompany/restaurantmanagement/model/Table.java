/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.model;



/**
 * [Member 3] Lớp đại diện cho một bàn ăn trong nhà hàng.
 * Lưu trạng thái bàn: trống hay đang có khách.
 */
public class Table {

    private int tableId;       // Mã bàn (duy nhất)
    private String tableName;  // Tên bàn (ví dụ: "Bàn 01")
    private boolean isOccupied; // true = đang có khách, false = trống

    // Constructor
    public Table(int tableId, String tableName) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.isOccupied = false; // Mặc định bàn trống khi mới tạo
    }

    // ── Getter ──────────────────────────────────────────
    public int getTableId()    { return tableId; }
    public String getTableName() { return tableName; }
    public boolean isOccupied()  { return isOccupied; }

    /**
     * Kiểm tra bàn có trống không (true = còn trống, có thể đặt).
     */
    public boolean checkAvailability() {
        return !isOccupied;
    }

    /**
     * Đặt trạng thái bàn (true = có khách, false = trống).
     */
    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    @Override
    public String toString() {
        return String.format("Bàn #%d | %s | %s",
                tableId,
                tableName,
                isOccupied ? "🔴 Có khách" : "🟢 Trống");
    }
}
