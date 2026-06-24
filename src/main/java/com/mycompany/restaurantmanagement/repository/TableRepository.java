package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * [Member 3] Repository - đọc/ghi danh sách Table xuống file data/tables.txt
 * Định dạng mỗi dòng:  id|tenBan|isOccupied
 */

public class TableRepository {

    private List<Table> tables;

    public TableRepository() {
        tables = new ArrayList<>();
    }

    public void add(Table t) {
        tables.add(t);
    }

    public Table findById(int id) {
        for (Table t : tables) {
            if (t.getTableId() == id) {
                return t;
            }
        }
        return null;
    }

    public List<Table> findAll() {
        return tables;
    }

    public List<Table> findAvailable() {

        List<Table> available = new ArrayList<>();

        for (Table t : tables) {
            if (t.checkAvailability()) {
                available.add(t);
            }
        }

        return available;
    }

    public boolean update(Table updated) {

        for (int i = 0; i < tables.size(); i++) {

            if (tables.get(i).getTableId() == updated.getTableId()) {
                tables.set(i, updated);
                return true;
            }
        }

        return false;
    }

    public boolean delete(int id) {

        for (Table t : tables) {

            if (t.getTableId() == id) {
                tables.remove(t);
                return true;
            }
        }

        return false;
    }

    public void loadFromFile(String path) {

        tables.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] d = line.split(",");

                int id = Integer.parseInt(d[0]);
                String name = d[1];
                int capacity = Integer.parseInt(d[2]);
                boolean occupied = Boolean.parseBoolean(d[3]);

                tables.add(
                        new Table(
                                id,
                                name,
                                capacity,
                                occupied
                        )
                );
            }

        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }

    public void saveToFile(String path) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

            for (Table t : tables) {

                bw.write(
                        t.getTableId() + "," +
                                t.getTableName() + "," +
                                t.getCapacity() + "," +
                                t.isOccupied()
                );

                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}