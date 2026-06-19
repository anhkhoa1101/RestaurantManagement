package com.mycompany.restaurantmanagement.repository;
 
import com.restaurant.model.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class CategoryRepository {

    private final List<Category> categories = new ArrayList<>();
    private int nextId = 1; // Biến để theo dõi ID tự động tăng

    // Phương thức để tạo ID tự động tăng
    public int nextId() {
        return nextId++;
    }
    // Phương thức để thêm một danh mục mới
    public void save(Category category) {
        categories.add(category);
    }
    // Phương thức để lấy tất cả các danh mục
    public List<Category> findAll() {
        return categories;
    }
    // Phương thức để tìm danh mục theo ID
    public Optional<Category> findById(int id) {
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst();
    }
    // Phương thức để xóa danh mục theo ID
    public boolean deleteById(int id) {
        return categories.removeIf(category -> category.getId() == id);
    }
}
