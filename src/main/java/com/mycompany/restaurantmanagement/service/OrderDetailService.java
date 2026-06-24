package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;
import com.mycompany.restaurantmanagement.repository.OrderRepository;

public class OrderDetailService {

    private InventoryService inventoryService;
    private OrderRepository  orderRepository;

    public OrderDetailService(InventoryService inventoryService,
                              OrderRepository orderRepository) {
        this.inventoryService = inventoryService;
        this.orderRepository  = orderRepository;
    }

    public OrderDetail addDetail(Order order, MenuItem item, int qty) {
        if (!validateStock(item, qty)) {
            throw new IllegalStateException("Không đủ tồn kho");
        }

        boolean reduced = inventoryService.reduceStock(item.getItemId(), qty);
        if (!reduced) {
            throw new IllegalStateException("Không thể cập nhật kho");
        }

        order.addDetail(item, qty);
        order.calculateTotal();
        orderRepository.update(order);
        orderRepository.saveToFile();

        for (OrderDetail detail : order.getDetails()) {
            if (detail.getMenuItem().getItemId() == item.getItemId()) {
                return detail;
            }
        }
        return null;
    }

    public void updateDetail(Order order, MenuItem item, int qty) {
        if (!validateStock(item, qty)) {
            throw new IllegalStateException("Không đủ tồn kho");
        }

        order.updateDetailQuantity(item, qty);
        order.calculateTotal();
        inventoryService.reduceStock(item.getItemId(), qty);
        orderRepository.update(order);
        orderRepository.saveToFile();
    }

    public void removeDetail(Order order, MenuItem item) {
        order.removeDetail(item);
        order.calculateTotal();
        orderRepository.update(order);
        orderRepository.saveToFile();
    }

    public boolean validateStock(MenuItem item, int qty) {
        if (qty <= 0) return false;
        return inventoryService.isInStock(item.getItemId());
    }
}