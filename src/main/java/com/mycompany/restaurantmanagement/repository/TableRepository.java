package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.Table;

import java.lang.String;
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

        String[] d = line.split("\\|");
        // FORMAT MỚI:
        // id|name|capacity|occupied
        if (d.length == 4) {

            return new Table(
                    Integer.parseInt(d[0].trim()),

                    d[1].trim(),

                    Integer.parseInt(d[2].trim()),

                    Boolean.parseBoolean(d[3].trim()));

        }

        throw new IllegalArgumentException("Sai định dạng tables.txt: " + line);

    }

    @Override
    protected String toLine(Table table) {

        return table.getTableId() + "|" + table.getTableName() + "|" + table.getCapacity() + "|" + table.isOccupied();

    }

}