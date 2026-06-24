package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.TableRepository;

import java.util.List;

public class TableService {

    private TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    public List<Table> getAvailableTables() {
        return tableRepository.findAvailable();
    }

    public boolean assignTable(int tableId) {

        Table table = tableRepository.findById(tableId);

        if (table == null) {
            return false;
        }

        if (table.isOccupied()) {
            return false;
        }

        table.setOccupied(true);

        return tableRepository.update(table);
    }

    public boolean releaseTable(int tableId) {

        Table table = tableRepository.findById(tableId);

        if (table == null) {
            return false;
        }

        if (!table.isOccupied()) {
            return false;
        }

        table.setOccupied(false);

        return tableRepository.update(table);
    }

    public Table addTable(String name, int capacity) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên bàn không được để trống");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Sức chứa phải > 0");
        }

        int newId = tableRepository.findAll().size() + 1;

        Table table = new Table(newId, name, capacity, false);

        tableRepository.add(table);

        return table;
    }
}