package com.mycompany.restaurantmanagement.repository;

import java.util.List;

public interface IRepository<T, ID> {

    List<T> findAll();

    T findById(ID id);

    void save(T entity);

    void delete(ID id);

}