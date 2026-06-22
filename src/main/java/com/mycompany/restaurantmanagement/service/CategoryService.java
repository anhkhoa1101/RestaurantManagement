package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;

public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    // Tạo mới một danh mục
    public Category addCategory(String name, String description) {
        Category category = new Category(repository.nextId(), name, description);
        repository.save(category);
        return category;
    }

    // Cập nhật thông tin danh mục
    public boolean updateCategory(int id, String newName, String newDescription) {
        Optional<Category> found = repository.findById(id);
        if (!found.isPresent())
            return false;
        found.get().setName(newName);
        found.get().setDescription(newDescription);
        return true;
    }

    // Xóa một danh mục
    public boolean deleteCategory(int id) {
        return repository.deleteById(id);
    }

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    // Lấy thông tin một danh mục theo ID
    public Optional<Category> getCategoryById(int id) {
        return repository.findById(id);
    }
}
