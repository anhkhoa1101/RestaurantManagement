package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) {
                return Optional.of(o);
            }
        }
        return Optional.empty();
    }

    public List<Order> findByTable(int tableId) {
        List<Order> result = new ArrayList<Order>();
        for (Order o : orders) {
            if (o.getTable().getTableId() == tableId) {
                result.add(o);
            }
        }
        return result;
    }

    public List<Order> findUnpaidOrders() {
        List<Order> result = new ArrayList<Order>();
        for (Order o : orders) {
            if (!o.isPaid()) {
                result.add(o);
            }
        }
        return result;
    }

    public void update() {
        saveToFile();
    }

    public boolean deleteById(String orderId) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(orderId)) {
                orders.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int max = 0;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split("\\|");

                String orderId = p[0];
                int tableId = Integer.parseInt(p[1]);
                boolean paid = Boolean.parseBoolean(p[2]);

                Table table = new Table(tableId, "Table " + tableId);

                Order order = new Order(orderId, table);
                order.setPaid(paid);

                orders.add(order);

                try {
                    int num = Integer.parseInt(orderId.substring(3));
                    if (num > max) max = num;
                } catch (Exception e) {}
            }

            nextId = max + 1;

        } catch (Exception e) {
            System.out.println("Error reading orders.txt: " + e.getMessage());
        }
    }

    private void saveToFile() {
        new File("data").mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            for (Order o : orders) {
                pw.println(o.getOrderId() + "|" + o.getTable().getTableId() + "|" + o.isPaid());
            }
        } catch (Exception e) {
            System.out.println("Error writing orders.txt: " + e.getMessage());
        }
    }
}