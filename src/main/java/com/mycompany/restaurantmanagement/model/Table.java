/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.model;

/**
 *
 * @author khoa0
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.model;

/**
 *
 * @author khoa0
 */
package com.mycompany.restaurant.model;

public class Table {
    
    private int tableId;
    private String tableName;
    private boolean isOccupied; // true: bàn có khách, false: bàn trống

    // Constructor: Khởi tạo bàn ăn mới
    public Table(int tableId, String tableName) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.isOccupied = false; // Mới tạo ra thì bàn luôn trống
    }

    
    public int getTableId() {
        return this.tableId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public boolean isOccupied() {
        return this.isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    // Hàm kiểm tra bàn có sẵn để ngồi hay không 
    public boolean checkAvailability() {
        if (this.isOccupied == false) {
            return true;  // Bàn trống -> Có sẵn
        } else {
            return false; // Bàn đã có khách -> Không có sẵn
        }
    }
@Override
public String toString() {
    String status = "Available"; // Có sẵn bàn
    if (this.isOccupied) {
        status = "Occupied";     //Có người ngồi
    }
    return "TableId: " + this.tableId + " [" + this.tableName + "] - Status: " + status;
}
