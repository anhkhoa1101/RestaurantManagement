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

    private static final String FILE_PATH = "data/tables.txt";

    private List<Table> tables;
    private int nextId;

    public TableRepository() {
        this.tables = new ArrayList<>();
        this.nextId = 1;
        loadFromFile(); // nạp dữ liệu cũ (nếu có) ngay khi khởi tạo
    }

    // Sinh id tự tăng cho bàn mới
    public int nextId() {
        return nextId++;
    }

    // Thêm bàn mới và ghi luôn xuống file
    public void save(Table table) {
        tables.add(table);
        saveToFile();
    }

    public List<Table> findAll() {
        return tables;
    }

    public Optional<Table> findById(int id) {
        return tables.stream()
                .filter(t -> t.getTableId() == id)
                .findFirst();
    }

    // Gọi hàm này sau khi 1 đối tượng Table (lấy từ findById/findAll) bị đổi trạng thái
    // (vd setOccupied) để ghi lại toàn bộ danh sách xuống file
    public void update() {
        saveToFile();
    }

    public boolean deleteById(int id) {
        boolean removed = tables.removeIf(t -> t.getTableId() == id);
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // chưa có file -> coi như chưa có dữ liệu, không lỗi
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                boolean occupied = Boolean.parseBoolean(parts[2]);

                Table t = new Table(id, name);
                t.setOccupied(occupied);
                tables.add(t);

                if (id >= nextId) {
                    nextId = id + 1; // đảm bảo id tiếp theo không bị trùng với dữ liệu cũ
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file bàn: " + e.getMessage());
        }
    }

    private void saveToFile() {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs(); // tự tạo thư mục data/ nếu chưa tồn tại
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Table t : tables) {
                bw.write(t.getTableId() + "|" + t.getTableName() + "|" + t.isOccupied());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file bàn: " + e.getMessage());
        }
    }
}