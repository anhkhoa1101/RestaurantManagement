
package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository quản lý Order
 * Lưu dữ liệu xuống: data/orders.txt
 */
public class OrderRepository {

    private static final String FILE_PATH = "data/orders.txt";

    private List<Order> orders;
    private int nextId;

    public OrderRepository() {
        orders = new ArrayList<Order>();
        nextId = 1;
        loadFromFile();
    }

    public String nextId() {
        return String.format("ORD%03d", nextId++);
    }

    public void save(Order order) {
        orders.add(order);
        saveToFile();
    }

    public List<Order> findAll() {
        return new ArrayList<Order>(orders);
    }

    public Optional<Order> findById(String orderId) {
        for (Order o : orders)
            if (o.getOrderId().equals(orderId))
                return Optional.of(o);

        return Optional.empty();
    }

    public List<Order> findByTable(int tableId) {
        List<Order> result = new ArrayList<Order>();

        for (Order o : orders)
            if (o.getTable().getTableId() == tableId)
                result.add(o);

        return result;
    }

    public List<Order> findUnpaidOrders() {
        List<Order> result = new ArrayList<Order>();

        for (Order o : orders)
            if (!o.isPaid())
                result.add(o);

        return result;
    }

    public void update() {
        saveToFile();
    }

    public boolean deleteById(String orderId) {
        for (int i = 0; i < orders.size(); i++)
            if (orders.get(i).getOrderId().equals(orderId)) {
                orders.remove(i);
                saveToFile();
                return true;
            }

        return false;
    }
}
