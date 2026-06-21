package com.mycompany.restaurantmanagement.service;



import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.TableRepository;
import java.util.ArrayList;
import java.util.List;

public class TableService {
    private TableRepository tableRepository;

    
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
        
        if (this.tableRepository.findAll().isEmpty()) {
            for (int i = 1; i <= 5; i++) {
                this.tableRepository.save(new Table(i, "Table No " + i));
            }
        }
    }

   
    public List<Table> getAllTables() {
        return this.tableRepository.findAll();
    }

    
    public List<Table> getAvailableTables() {
        List<Table> allTables = this.tableRepository.findAll();
        List<Table> availableTables = new ArrayList<>();
        
        for (int i = 0; i < allTables.size(); i++) {
            Table table = allTables.get(i);
            if (table.checkAvailability() == true) {
                availableTables.add(table);
            }
        }
        return availableTables;
    }

    
    public Table findTableById(int tableId) {
        List<Table> allTables = this.tableRepository.findAll();
        for (int i = 0; i < allTables.size(); i++) {
            Table table = allTables.get(i);
            if (table.getTableId() == tableId) {
                return table;
            }
        }
        return null; 
    }
}
