package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.IOException;


public class OrderRepository {

    private List<Order> orders;

    public OrderRepository() {
        orders = new ArrayList<>();
    }

    public void add(Order o) {
        orders.add(o);
    }

    public Order findById(String id) {
        for (Order o : orders) {
            if (o.getOrderId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    public List<Order> findAll() {
        return orders;
    }

    public List<Order> findByTable(int tableId) {
        List<Order> result = new ArrayList<>();

        for (Order o : orders) {
            if (o.getTable() != null && o.getTable().getTableId() == tableId) {
                result.add(o);
            }
        }

        return result;
    }

    public List<Order> findUnpaidOrders() {
        List<Order> result = new ArrayList<>();

        for (Order o : orders) {
            if (!o.isPaid()) {
                result.add(o);
            }
        }

        return result;
    }

    public boolean update(Order updated) {

        for (int i = 0; i < orders.size(); i++) {

            if (orders.get(i).getOrderId().equals(updated.getOrderId())) {
                orders.set(i, updated);
                return true;
            }
        }

        return false;
    }

    public boolean delete(String id) {

        for (Order o : orders) {

            if (o.getOrderId().equals(id)) {
                orders.remove(o);
                return true;
            }
        }

        return false;
    }

    public void loadFromFile(String path) {

        orders.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] d = line.split(",");

                String orderId = d[0];
                boolean paid = Boolean.parseBoolean(d[1]);

                Order order = new Order(orderId, null);

                order.setPaid(paid);

                orders.add(order);
            }

        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }

    public void saveToFile(String path) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

            for (Order o : orders) {

                bw.write(
                        o.getOrderId() + "," +
                                o.isPaid()
                );

                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}