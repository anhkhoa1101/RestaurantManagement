package com.mycompany.restaurantmanagement.repository;
import com.mycompany.restaurantmanagement.model.Table;
import java.util.ArrayList;
import java.util.List;
public class TableRepository {
    private final List<Table> tableList = new ArrayList<>();
    public List<Table> findAll() {
        return this.tableList;
    }
    public void save(Table table) {
        if (table != null) {
            this.tableList.add(table);
        }
    }
    public Table findById(int tableId) {
        for (int i=0; i < tableList.size(); i++) {
            Table currentTable = tableList.get(i);
            if (currentTable.getTableId() == tableId) {
                return currentTable;
            }
        }
        return null;
    }
}
