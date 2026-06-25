package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Category;
import com.mycompany.restaurantmanagement.repository.CategoryRepository;

public class CategoryService extends BaseService<Category, Integer> {

    private final CategoryRepository categoryRepository;
    //───Constructor──────────────────────────────────────────────────────────────
    public CategoryService(CategoryRepository repository) {
        super(repository);
        this.categoryRepository = repository;
    }

    // thêm danh mục mới
    public Category addCategory(String name, String description) {
        Category category = new Category(categoryRepository.nextId(), name, description);
        add(category); // Gọi hàm add(T entity) từ BaseService để lưu và ghi file
        return category;
    }

    // tự động tạo ID mới và lưu vào file
    public boolean updateCategory(Integer id, String newName, String newDescription) {
        Category found = getById(id); // Gọi hàm getById(ID id) từ BaseService
        if (found == null) {
            return false;
        }
        found.setName(newName);
        found.setDescription(newDescription);
        update(found); // Gọi hàm update(T entity) từ BaseService để đồng bộ xuống file
        return true;
    }
}