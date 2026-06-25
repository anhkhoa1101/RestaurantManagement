package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends BaseRepository<Order, String> {

    public OrderRepository() {

        super(AppConfig.ORDERS_FILE_PATH);

    }

    @Override
    public Order findById(String id) {

        for (Order o : data) {

            if (o.getOrderId().equals(id)) {

                return o;

            }

        }

        return null;

    }

//    @Override
//    public void save(Order entity) {
//
//        Order old = findById(entity.getOrderId());
//
//        if (old != null) {
//
//            data.remove(old);
//
//        }
//
//        data.add(entity);
//
//        saveToFile();
//
//    }

    @Override
    protected Order parseLine(String line) {

        String[] d = line.split("|");

        String orderId = d[0];

        boolean paid = Boolean.parseBoolean(d[1]);

        Order order = new Order(orderId, null);

        order.setPaid(paid);

        loadOrderDetails(order);

        order.calculateTotal();

        return order;

    }

    @Override
    protected String toLine(Order order) {

        return order.getOrderId() + "|" + order.isPaid();

    }

    public List<Order> findByTable(int tableId) {

        List<Order> result = new ArrayList<>();

        for (Order o : data) {

            if (o.getTable() != null && o.getTable().getTableId() == tableId) {

                result.add(o);

            }

        }

        return result;

    }

    public List<Order> findUnpaidOrders() {

        List<Order> result = new ArrayList<>();

        for (Order o : data) {

            if (!o.isPaid()) {

                result.add(o);

            }

        }

        return result;

    }

    private void loadOrderDetails(Order order) {

        try (
                BufferedReader br = new BufferedReader(new FileReader(AppConfig.ORDER_DETAILS_FILE_PATH))
        ) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] d = line.split("|");

                if (!d[0].equals(order.getOrderId())) {

                    continue;

                }

                MenuItem item = new MenuItem(Integer.parseInt(d[1]), d[2], "", Double.parseDouble(d[3]), null);

                order.addDetail(item, Integer.parseInt(d[4]));

            }

        } catch (IOException e) {

            System.out.println("Load order details failed: " + e.getMessage());

        }

    }

}