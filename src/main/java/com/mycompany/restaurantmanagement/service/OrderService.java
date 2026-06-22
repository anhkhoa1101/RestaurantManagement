
package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.OrderRepository;
import com.mycompany.restaurantmanagement.repository.TableRepository;

import java.util.List;
import java.util.Optional;

public class OrderService {

    private OrderRepository orderRepository;

    private TableRepository tableRepository;

    public OrderService(
            OrderRepository orderRepository,
            TableRepository tableRepository
    ) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
    }

    // Tạo đơn mới cho bàn
    public Order createOrder(int tableId) {

        Optional<Table> found = tableRepository.findById(tableId);

        if (!found.isPresent())
            return null;

        Table table = found.get();

        if (!table.checkAvailability())
            return null;

        table.setOccupied(true);

        tableRepository.update();

        Order order = new Order(orderRepository.nextId(), table);

        orderRepository.save(order);

        return order;
    }

    // Hủy đơn
    public boolean cancelOrder(String orderId) {

        Optional<Order> found =
                orderRepository.findById(orderId);

        if (!found.isPresent())
            return false;

        Order order = found.get();

        if (order.isPaid())
            return false;

        order.getTable().setOccupied(false);

        tableRepository.update();

        return orderRepository.deleteById(orderId);
    }

    // Đóng đơn (thanh toán xong)
    public boolean closeOrder(String orderId) {

        Optional<Order> found = orderRepository.findById(orderId);

        if (!found.isPresent())
            return false;

        Order order = found.get();

        order.setPaid(true);

        order.getTable().setOccupied(false);

        orderRepository.update();

        tableRepository.update();

        return true;
    }

    // Lấy tất cả đơn
    public List<Order> getAllOrders() { return orderRepository.findAll(); }

    // Lấy danh sách đơn theo bàn
    public List<Order> getOrdersByTable(int tableId) {return orderRepository.findByTable(tableId); }

    // Tìm đơn theo ID
    public Optional<Order> getOrderById(String orderId) { return orderRepository.findById(orderId); }

    // Tính tổng tiền đơn
    public double getOrderTotal(String orderId) {
        Optional<Order> found = orderRepository.findById(orderId);
        if (!found.isPresent())
            return 0;
        return found.get().getTotalPrice();
    }
}
