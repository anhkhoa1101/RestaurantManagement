package com.mycompany.restaurantmanagement.service;

import java.util.List;

public interface IService<T, ID> {

    List<T> getAll();

    T getById(ID id);

    void add(T entity);

    void update(T entity);

    void remove(ID id);

}