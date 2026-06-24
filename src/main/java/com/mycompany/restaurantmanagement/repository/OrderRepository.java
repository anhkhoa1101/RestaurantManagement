package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;
import com.mycompany.restaurantmanagement.config.AppConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Nạp dữ liệu từ 2 file:
     * 1. orders.txt        -> orderId, paid
     * 2. order_details.txt -> orderId, itemId, itemName, price, qty
     */
    public void loadFromFile() {

        orders.clear();

        // ---- 1. Đọc orders.txt -> tạo Order cơ bản (chưa có món) ----
        try (BufferedReader br = new BufferedReader(new FileReader(AppConfig.ORDERS_FILE_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");

                String orderId = d[0];
                boolean paid = Boolean.parseBoolean(d[1]);

                Order order = new Order(orderId, null);
                order.setPaid(paid);

                orders.add(order);
            }

        } catch (IOException e) {
            System.out.println("Lỗi đọc file orders: " + e.getMessage());
        }

        // ---- 2. Đọc order_details.txt -> gắn món vào Order tương ứng ----
        try (BufferedReader br = new BufferedReader(new FileReader(AppConfig.ORDER_DETAILS_FILE_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");

                String orderId  = d[0];
                int    itemId   = Integer.parseInt(d[1]);
                String itemName = d[2];
                double price    = Double.parseDouble(d[3]);
                int    qty      = Integer.parseInt(d[4]);

                Order order = findById(orderId);

                if (order == null) {
                    continue;
                }

                MenuItem item = new MenuItem(itemId, itemName, "", price, null);
                order.addDetail(item, qty);
            }

        } catch (IOException e) {
            System.out.println("Lỗi đọc file order details: " + e.getMessage());
        }

        // ---- 3. Tính lại tổng tiền sau khi đã nạp đủ chi tiết món ----
        for (Order o : orders) {
            o.calculateTotal();
        }
    }

    /**
     * Ghi dữ liệu ra 2 file: orders.txt và order_details.txt
     */
    public void saveToFile() {

        // ---- 1. Ghi orders.txt ----
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(AppConfig.ORDERS_FILE_PATH))) {

            for (Order o : orders) {

                bw.write(o.getOrderId() + "," + o.isPaid());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Lỗi ghi file orders: " + e.getMessage());
        }

        // ---- 2. Ghi order_details.txt ----
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(AppConfig.ORDER_DETAILS_FILE_PATH))) {

            for (Order o : orders) {

                for (OrderDetail d : o.getDetails()) {

                    MenuItem item = d.getMenuItem();

                    bw.write(
                            o.getOrderId() + "," +
                                    item.getItemId() + "," +
                                    item.getName() + "," +
                                    item.getPrice() + "," +
                                    d.getQuantity()
                    );

                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Lỗi ghi file order details: " + e.getMessage());
        }
    }
}