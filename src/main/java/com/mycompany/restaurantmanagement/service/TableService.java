
package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.TableRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableService {

    private TableRepository repository;

    public TableService(TableRepository repository) {
        this.repository = repository;
    }

    // Tạo bàn mới
    public Table addTable(String tableName) {

        Table table = new Table(repository.nextId(), tableName);

        repository.save(table);

        return table;
    }

    // Đổi tên bàn
    public boolean updateTableName(int tableId, String newName) {

        Optional<Table> found = repository.findById(tableId);

        if (!found.isPresent())
            return false;

        Table table = found.get();

        table = new Table(table.getTableId(), newName);

        repository.update();

        return true;
    }

    // Xóa bàn
    public boolean deleteTable(int tableId) {

        return repository.deleteById(tableId);
    }

    // Lấy toàn bộ bàn
    public List<Table> getAllTables() {

        return repository.findAll();
    }

    // Lấy bàn đang trống
    public List<Table> getAvailableTables() {

        List<Table> result = new ArrayList<Table>();

        for (Table t : repository.findAll()) {

            if (t.checkAvailability())
                result.add(t);
        }

        return result;
    }

    // Lấy bàn đang có khách
    public List<Table> getOccupiedTables() {

        List<Table> result = new ArrayList<Table>();

        for (Table t : repository.findAll()) {

            if (t.isOccupied())
                result.add(t);
        }

        return result;
    }

    // Tìm bàn theo ID
    public Optional<Table> getTableById(int tableId) {

        return repository.findById(tableId);
    }

    // Đánh dấu bàn có khách
    public boolean occupyTable(int tableId) {

        Optional<Table> found = repository.findById(tableId);

        if (!found.isPresent())
            return false;

        Table table = found.get();

        if (table.isOccupied())
            return false;

        table.setOccupied(true);

        repository.update();

        return true;
    }

    // Giải phóng bàn
    public boolean releaseTable(int tableId) {

        Optional<Table> found = repository.findById(tableId);

        if (!found.isPresent())
            return false;

        Table table = found.get();

        if (!table.isOccupied())
            return false;

        table.setOccupied(false);

        repository.update();

        return true;
    }
}
