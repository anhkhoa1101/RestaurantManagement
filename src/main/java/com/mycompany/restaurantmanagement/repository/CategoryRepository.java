package com.mycompany.restaurantmanagement.repository;
 
import com.mycompany.restaurantmanagement.model.Category;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository {

    // Đường dẫn đến file lưu trữ danh mục
    private static final String FILE_PATH = "data/categories.txt";

    private final List<Category> categories = new ArrayList<>();
    private int nextId = 1; // Biến để theo dõi ID tự động tăng

    // Constructor để tải dữ liệu từ file khi khởi tạo repository
    public CategoryRepository() {
        loadFromFile();
    }
    // Phương thức để tạo ID tự động tăng
    public int nextId() {
        return nextId++;
    }
    // Lưu vào danh sách và ghi vào file
    public void save(Category category) {
        categories.add(category);
        saveToFile();
    }
    // Xoá khỏi danh sách và cập nhật file
    public boolean deleteById(int id) {
        boolean removed = categories.removeIf(c -> c.getId() == id);
        if (removed) saveToFile();
        return removed;
    }
    // ─── Đọc từ file ─────────────────────────────────────────────────────────
    // Phương thức để tải dữ liệu từ file vào danh sách
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return; // Lần đầu chạy chưa có file — bỏ qua
 
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            int maxId = 0;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue; // Dòng lỗi định dạng — bỏ qua
 
                int id          = Integer.parseInt(parts[0].trim());
                String name     = parts[1].trim();
                String desc     = parts[2].trim();
                boolean isActive= Boolean.parseBoolean(parts[3].trim());
 
                Category c = new Category(id, name, desc);
                c.setActive(isActive);
                categories.add(c);
 
                if (id > maxId) maxId = id; // Cập nhật ID lớn nhất
            }
            nextId = maxId + 1; // Đảm bảo ID tiếp theo không bị trùng
        } catch (IOException e) {
            System.out.println("Lỗi đọc file categories.txt: " + e.getMessage());
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) { /* bỏ qua */ }
            }
        }
    }
     // ─── Ghi ra file ─────────────────────────────────────────────────────────
    private void saveToFile() {
        // Tạo thư mục data/ nếu chưa có
        new File("data").mkdirs();
 
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(FILE_PATH, false));
            for (Category c : categories) {
                // Định dạng: id|name|description|isActive
                writer.println(c.getId() + "|" + c.getName() + "|"
                    + c.getDescription() + "|" + c.isActive());
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file categories.txt: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    }
}