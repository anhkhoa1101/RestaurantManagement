
package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;
import com.mycompany.restaurantmanagement.repository.MenuItemRepository;
import com.mycompany.restaurantmanagement.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailService {

    private OrderRepository orderRepository;

    private MenuItemRepository menuItemRepository;

    private InventoryService inventoryService;

    public OrderDetailService(
            OrderRepository orderRepository,
            MenuItemRepository menuItemRepository,
            InventoryService inventoryService
    ) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.inventoryService = inventoryService;
    }

    // Thêm món vào đơn
    public boolean addItemToOrder(String orderId, int menuItemId, int quantity) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (!orderOpt.isPresent())
            return false;

        Optional<MenuItem> itemOpt = menuItemRepository.findById(menuItemId);

        if (!itemOpt.isPresent())
            return false;

        if (!inventoryService.isInStock(menuItemId))
            return false;

        if (!inventoryService.reduceStock(menuItemId, quantity))
            return false;

        Order order = orderOpt.get();

        MenuItem item = itemOpt.get();

        if (order.isPaid())
            return false;

        order.addDetail(item, quantity);

        orderRepository.update();

        return true;
    }

    // Xóa món khỏi đơn
    public boolean removeItemFromOrder(String orderId, int menuItemId) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (!orderOpt.isPresent())
            return false;

        Optional<MenuItem> itemOpt = menuItemRepository.findById(menuItemId);

        if (!itemOpt.isPresent())
            return false;

        Order order = orderOpt.get();

        if (order.isPaid())
            return false;

        order.removeDetail(itemOpt.get());

        orderRepository.update();

        return true;
    }

    // Cập nhật số lượng món
    public boolean updateItemQuantity(String orderId, int menuItemId, int newQuantity) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (!orderOpt.isPresent())
            return false;

        Order order = orderOpt.get();

        if (order.isPaid())
            return false;

        for (OrderDetail detail : order.getDetails()) {

            if (detail.getMenuItem().getItemId() == menuItemId) {
                detail.setQuantity(newQuantity);
                order.calculateTotal();
                orderRepository.update();

                return true;
            }
        }

        return false;
    }

    // Lấy danh sách chi tiết đơn
    public List<OrderDetail> getOrderDetails(String orderId) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (!orderOpt.isPresent())
            return new ArrayList<OrderDetail>();

        return orderOpt.get().getDetails();
    }

    // Lấy thành tiền của 1 dòng món
    public double getSubTotal(String orderId, int menuItemId) {

        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (!orderOpt.isPresent())
            return 0;

        for (OrderDetail detail : orderOpt.get().getDetails()) {

            if (detail.getMenuItem().getItemId() == menuItemId)
                return detail.getSubTotal();
        }

        return 0;
    }
}
