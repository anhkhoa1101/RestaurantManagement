package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService {

    private OrderRepository orderRepository;
    private TableService tableService;
    private InventoryService inventoryService;

    public OrderService(OrderRepository orderRepository, TableService tableService, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.tableService = tableService;
        this.inventoryService = inventoryService;
    }

    public Order createOrder(int tableId) {

        boolean assigned = tableService.assignTable(tableId);

        if (!assigned) {
            throw new IllegalStateException("Bàn không khả dụng");
        }

        Order order = new Order(UUID.randomUUID().toString(), tableService.getAllTables().stream().filter(t -> t.getTableId() == tableId).findFirst().orElse(null));

        orderRepository.add(order);

        return order;
    }

    public void addItemToOrder(String orderId, MenuItem item, int qty) {

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Không tìm thấy đơn");
        }

        boolean success = inventoryService.reduceStock(item.getItemId(), qty);

        if (!success) {
            throw new IllegalStateException("Không đủ tồn kho");
        }

        order.addDetail(item, qty);

        order.calculateTotal();

        orderRepository.update(order);
    }

    public void removeItemFromOrder(String orderId, MenuItem item) {

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Không tìm thấy đơn");
        }

        order.removeDetail(item);

        order.calculateTotal();

        orderRepository.update(order);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOpenOrders() {
        return orderRepository.findUnpaidOrders();
    }

    public Order checkoutOrder(String orderId) {

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Không tìm thấy đơn");
        }

        order.calculateTotal();

        order.setPaid(true);

        tableService.releaseTable(order.getTable().getTableId());

        orderRepository.update(order);

        return order;
    }

    public void cancelOrder(String orderId) {

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            return;
        }

        if (order.getTable() != null) {

            tableService.releaseTable(order.getTable().getTableId());
        }

        orderRepository.delete(orderId);
    }
}