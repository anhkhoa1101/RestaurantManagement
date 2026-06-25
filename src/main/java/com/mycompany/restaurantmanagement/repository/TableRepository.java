package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.Table;

import java.util.ArrayList;
import java.util.List;

public class TableRepository extends BaseRepository<Table, Integer> {

    public TableRepository() {

        super(AppConfig.TABLES_FILE_PATH);

    }

    @Override
    public Table findById(Integer id) {

        for (Table table : data) {

            if (table.getTableId() == id) {

                return table;

            }

        }

        return null;

    }

    @Override
    public void save(Table entity) {

        Table old = findById(entity.getTableId());

        if (old != null) {

            data.remove(old);

        }

        data.add(entity);

        saveToFile();

    }

    public List<Table> findAvailable() {

        List<Table> available = new ArrayList<>();

        for (Table table : data) {

            if (table.checkAvailability()) {

                available.add(table);

            }

        }

        return available;

    }

    @Override
    protected Table parseLine(String line) {

        String[] d = line.split(",");

        int id = Integer.parseInt(d[0]);

        String name = d[1];

        int capacity = Integer.parseInt(d[2]);

        boolean occupied = Boolean.parseBoolean(d[3]);

        return new Table(id, name, capacity, occupied);

    }

    @Override
    protected String toLine(Table table) {

        return table.getTableId() + "," + table.getTableName() + "," + table.getCapacity() + "," + table.isOccupied();

    }

}