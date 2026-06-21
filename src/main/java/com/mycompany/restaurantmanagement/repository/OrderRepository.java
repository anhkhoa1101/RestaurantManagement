package com.mycompany.restaurantmanagement.repository;
import com.mycompany.restaurantmanagement.model.Order;
import java.util.ArrayList;
import java.util.List;
public class OrderRepository {
    private final List<Order> orderList = new ArrayList<>();
    public List<Order> findAll() {
        return this.orderList;
    }
    public void save(Order order) {
        if (order !=null) {
            this.orderList.add(order);
        }
    }
    public Order findById(String orderId) {
        for (int i=0; i < orderList.size(); i++) {
            Order currentOrder = orderList.get(i);
            if (currentOrder.getOrderId().equals(orderId)) {
                return currentOrder;
            }
        }
        return null;
    }
}

