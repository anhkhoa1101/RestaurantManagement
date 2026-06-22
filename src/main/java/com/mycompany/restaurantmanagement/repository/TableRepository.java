package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * [Member 3] Kho lưu trữ dữ liệu bàn ăn — đọc/ghi từ file tables.txt
 *
 * Định dạng mỗi dòng trong file:
 *   tableId,tableName,isOccupied
 * Ví dụ:
 *   1,Bàn 01,false
 *   2,Bàn 02,true
 */
public class TableRepository {

    // Đường dẫn tới file lưu dữ liệu bàn
    private static final String FILE_PATH = "data/tables.txt";

    // Danh sách bàn trong bộ nhớ
    private List<Table> tables = new ArrayList<>();

    // Constructor: tự động load dữ liệu từ file khi khởi tạo
    public TableRepository() {
        loadFromFile();
    }

    // ── Đọc dữ liệu từ file ─────────────────────────────
    private void loadFromFile() {
        tables.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // Bỏ dòng trắng

                String[] parts = line.split(",");
                // Mỗi dòng phải có đúng 3 phần
                if (parts.length < 3) continue;

                int id          = Integer.parseInt(parts[0].trim());
                String name     = parts[1].trim();
                boolean occupied = Boolean.parseBoolean(parts[2].trim());

                Table t = new Table(id, name);
                t.setOccupied(occupied);
                tables.add(t);
            }
        } catch (FileNotFoundException e) {
            // File chưa tồn tại → bắt đầu với danh sách rỗng, không lỗi
            System.out.println("[TableRepository] Chưa có file tables.txt, sẽ tạo mới khi lưu.");
        } catch (IOException e) {
            System.out.println("[TableRepository] Lỗi đọc file: " + e.getMessage());
        }
    }

    // ── Ghi toàn bộ danh sách ra file ───────────────────
    public void saveToFile() {
        // Đảm bảo thư mục data/ tồn tại
        new File("data").mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Table t : tables) {
                // Ghi theo định dạng: id,tên,isOccupied
                bw.write(t.getTableId() + "," + t.getTableName() + "," + t.isOccupied());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[TableRepository] Lỗi ghi file: " + e.getMessage());
        }
    }

    // ── CRUD cơ bản ─────────────────────────────────────

    /** Lấy toàn bộ danh sách bàn */
    public List<Table> findAll() {
        return tables;
    }

    /** Tìm bàn theo ID */
    public Table findById(int id) {
        for (Table t : tables) {
            if (t.getTableId() == id) return t;
        }
        return null; // Không tìm thấy
    }

    /** Thêm bàn mới vào danh sách và lưu file */
    public void add(Table table) {
        tables.add(table);
        saveToFile();
    }

    /** Lưu lại sau khi thay đổi trạng thái bàn (occupied) */
    public void update() {
        saveToFile(); // Chỉ cần ghi lại toàn bộ
    }
}