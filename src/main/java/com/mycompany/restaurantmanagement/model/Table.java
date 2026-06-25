package com.mycompany.restaurantmanagement.model;

import java.util.Objects;

/**
 * Model - Bàn ăn trong nhà hàng
 */
public class Table {

    private int tableId;

    private String tableName;

    private int capacity;

    private boolean isOccupied;

    public Table() {
    }

    public Table(int tableId, String tableName, int capacity, boolean isOccupied) {

        this.tableId = tableId;

        this.tableName = tableName;

        this.capacity = capacity;

        this.isOccupied = isOccupied;

    }
//  Getter
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
    // Setter
    public void setTableName(String tableName) {

        this.tableName = tableName;

    }

    public void setCapacity(int capacity) {

        this.capacity = capacity;

    }

    public void setOccupied(boolean occupied) {

        this.isOccupied = occupied;

    }

    public boolean checkAvailability() {

        return !isOccupied;

    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {

            return true;

        }

        if (obj == null || getClass() != obj.getClass()) {

            return false;

        }

        Table table = (Table) obj;

        return tableId == table.tableId;

    }

    @Override
    public int hashCode() {

        return Objects.hash(tableId);

    }

    @Override
    public String toString() {

        return "Table{" + "tableId=" + tableId + ", tableName='" + tableName + '\'' + ", capacity=" + capacity + ", isOccupied=" + isOccupied + '}';

    }

}