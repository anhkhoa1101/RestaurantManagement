package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.TableRepository;

import java.util.List;

public class TableService extends BaseService<Table, Integer> {

    public TableService(TableRepository repository) {

        super(repository);

    }

    public List<Table> getAvailableTables() {

        return ((TableRepository) repository).findAvailable();

    }

    public boolean assignTable(int tableId) {

        Table table = getById(tableId);

        if (table == null) {

            return false;

        }

        if (table.isOccupied()) {

            return false;

        }

        table.setOccupied(true);

        update(table);

        return true;

    }

    public boolean releaseTable(int tableId) {

        Table table = getById(tableId);

        if (table == null) {

            return false;

        }

        if (!table.isOccupied()) {

            return false;

        }

        table.setOccupied(false);

        update(table);

        return true;

    }

    public Table addTable(String name, int capacity) {

        validate(name, capacity);

        int newId = generateId();

        Table table = new Table(newId, name, capacity, false);

        add(table);

        return table;

    }

    public Table updateTable(int tableId, String newName, int newCapacity) {

        validate(newName, newCapacity);

        Table table = getById(tableId);

        if (table == null) {

            throw new IllegalArgumentException("Không tìm thấy bàn");

        }

        table.setTableName(newName);

        table.setCapacity(newCapacity);

        update(table);

        return table;

    }

    public boolean deleteTable(int tableId) {

        Table table = getById(tableId);

        if (table == null) {

            return false;

        }

        if (table.isOccupied()) {

            throw new IllegalStateException("Không thể xóa bàn đang sử dụng");

        }

        remove(tableId);

        return true;

    }

    private void validate(String name, int capacity) {

        if (name == null || name.trim().isEmpty()) {

            throw new IllegalArgumentException("Tên bàn không được để trống");

        }

        if (capacity <= 0) {

            throw new IllegalArgumentException("Sức chứa phải > 0");

        }

    }

    private int generateId() {

        int max = 0;

        for (Table table : getAll()) {

            if (table.getTableId() > max) {

                max = table.getTableId();

            }

        }

        return max + 1;

    }

}