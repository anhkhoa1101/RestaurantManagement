package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.repository.IRepository;

import java.util.List;

public abstract class BaseService<T, ID> implements IService<T, ID> {

    protected IRepository<T, ID> repository;

    public BaseService(IRepository<T, ID> repository) {

        this.repository = repository;

    }

    @Override
    public List<T> getAll() {

        return repository.findAll();

    }

    @Override
    public T getById(ID id) {

        return repository.findById(id);

    }

    @Override
    public void add(T entity) {

        repository.save(entity);

    }

    @Override
    public void update(T entity) {
        
        repository.save(entity);

    }

    @Override
    public void remove(ID id) {

        repository.delete(id);

    }

}