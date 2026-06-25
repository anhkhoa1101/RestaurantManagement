package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService extends BaseService<Order, String> {

    private final TableService tableService;

    private final InventoryService inventoryService;

    public OrderService(OrderRepository repository, TableService tableService, InventoryService inventoryService) {

        super(repository);

        this.tableService = tableService;

        this.inventoryService = inventoryService;

    }

    public Order createOrder(int tableId) {

        boolean assigned = tableService.assignTable(tableId);

        if (!assigned) {

            throw new IllegalStateException("Bàn không khả dụng");

        }

        Table table = tableService.getById(tableId);

        Order order = new Order(UUID.randomUUID().toString(), table);

        add(order);

        return order;

    }

    public void addItemToOrder(String orderId, MenuItem item, int qty) {

        Order order = getById(orderId);

        if (order == null) {

            throw new IllegalArgumentException("Không tìm thấy đơn");

        }

        boolean reduced = inventoryService.reduceStock(item.getItemId(), qty);

        if (!reduced) {

            throw new IllegalStateException("Không đủ tồn kho");

        }

        order.addDetail(item, qty);

        order.calculateTotal();

        update(order);

    }

    public void removeItemFromOrder(String orderId, MenuItem item) {

        Order order = getById(orderId);

        if (order == null) {

            throw new IllegalArgumentException("Không tìm thấy đơn");

        }

        order.removeDetail(item);

        order.calculateTotal();

        update(order);

    }

    public List<Order> getOpenOrders() {

        return ((OrderRepository) repository).findUnpaidOrders();

    }

    public Order checkoutOrder(String orderId) {

        Order order = getById(orderId);

        if (order == null) {

            throw new IllegalArgumentException("Không tìm thấy đơn");

        }

        order.calculateTotal();

        order.setPaid(true);

        if (order.getTable() != null) {

            tableService.releaseTable(order.getTable().getTableId());

        }

        update(order);

        return order;

    }

    public void cancelOrder(String orderId) {

        Order order = getById(orderId);

        if (order == null) {

            return;

        }

        if (order.getTable() != null) {

            tableService.releaseTable(order.getTable().getTableId());

        }

        remove(orderId);

    }

}