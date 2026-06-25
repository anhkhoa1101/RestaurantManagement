package com.mycompany.restaurantmanagement.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<T, ID> implements IRepository<T, ID> {

    protected List<T> data;

    protected String filePath;

    public BaseRepository(String filePath) {

        this.filePath = filePath;

        this.data = new ArrayList<>();

        loadFromFile();

    }

    @Override
    public List<T> findAll() {

        return new ArrayList<>(data);

    }

    @Override
    public abstract T findById(ID id);

    @Override
    public void save(T entity) {

        data.add(entity);

        saveToFile();

    }

    @Override
    public void delete(ID id) {

        T entity = findById(id);

        if (entity != null) {

            data.remove(entity);

            saveToFile();

        }

    }

    protected void loadFromFile() {

        data.clear();

        File file = new File(filePath);

        if (!file.exists()) {

            return;

        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (!line.isEmpty()) {

                    data.add(parseLine(line));

                }

            }

        } catch (IOException e) {

            System.out.println("Load failed: " + e.getMessage());

        }

    }

    protected void saveToFile() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            for (T entity : data) {

                bw.write(toLine(entity));

                bw.newLine();

            }

        } catch (IOException e) {

            System.out.println("Save failed: " + e.getMessage());

        }

    }
    //Using for loadFromFile
    protected abstract T parseLine(String line);

    //Using for saveToFile
    protected abstract String toLine(T entity);

}