package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;

public class OrderDetailService {

    private final InventoryService inventoryService;

    private final OrderService orderService;

    public OrderDetailService(InventoryService inventoryService, OrderService orderService) {

        this.inventoryService = inventoryService;

        this.orderService = orderService;

    }

    public OrderDetail addDetail(Order order, MenuItem item, int qty) {

        validateStock(item, qty);

        inventoryService.reduceStock(item.getItemId(), qty);

        order.addDetail(item, qty);

        order.calculateTotal();

        orderService.update(order);

        return findDetail(order, item.getItemId());

    }

    public void updateDetail(Order order, MenuItem item, int qty) {

        validateStock(item, qty);

        order.updateDetailQuantity(item, qty);

        order.calculateTotal();

        inventoryService.reduceStock(item.getItemId(), qty);

        orderService.update(order);

    }

    public void removeDetail(Order order, MenuItem item) {

        order.removeDetail(item);

        order.calculateTotal();

        orderService.update(order);

    }

    public boolean validateStock(MenuItem item, int qty) {

        if (qty <= 0) {

            throw new IllegalArgumentException("Số lượng phải > 0");

        }

        if (!inventoryService.isInStock(item.getItemId())) {

            throw new IllegalStateException("Không đủ tồn kho");

        }

        return true;

    }

    private OrderDetail findDetail(Order order, int itemId) {

        for (OrderDetail detail : order.getDetails()) {

            if (detail.getMenuItem().getItemId() == itemId) {

                return detail;

            }

        }

        return null;

    }

}