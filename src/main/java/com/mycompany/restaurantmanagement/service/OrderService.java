package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.model.Invoice;
import com.mycompany.restaurantmanagement.repository.OrderRepository;
import com.mycompany.restaurantmanagement.service.InvoiceService;

import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService extends BaseService<Order, String> {

    private final InvoiceService invoiceService;

    private final TableService tableService;

    private final InventoryService inventoryService;

    public OrderService(OrderRepository repository, TableService tableService, InventoryService inventoryService, InvoiceService invoiceService) {

        super(repository);

        this.tableService = tableService;

        this.inventoryService = inventoryService;

        this.invoiceService = invoiceService;

    }

    public Order createOrder(int tableId) {

        boolean assigned = tableService.assignTable(tableId);

        if (!assigned) {

            throw new IllegalStateException("Bàn không khả dụng");

        }

        Table table = tableService.getById(tableId);

        Random random = new Random();

        String orderId;

        do {

            int number = 100000 + random.nextInt(900000);

            orderId = String.valueOf(number);

        } while (getById(orderId) != null);

        Order order = new Order(orderId, table);

        add(order);

        invoiceService.createInvoiceFromOrder(order);
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

        // ⭐ THIẾU DÒNG NÀY
        update(order);

        Invoice invoice = invoiceService.getByOrderId(orderId);

        if (invoice != null) {

            invoice.setTotalAmount(order.getTotalPrice());

            invoiceService.update(invoice);

        }

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

    // Thêm vào OrderService, dùng lại getOpenOrders() đã có sẵn
    public List<Order> getOpenOrdersByTable(int tableId) {
        return getOpenOrders().stream().filter(o -> o.getTable().getTableId() == tableId).collect(Collectors.toList());
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