package com.mycompany.restaurantmanagement.service;



import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.OrderRepository;
import java.util.List;

public class OrderService {
    private OrderRepository orderRepository;
    private TableService tableService; 
    private int orderCounter = 1;      

    public OrderService(OrderRepository orderRepository, TableService tableService) {
        this.orderRepository = orderRepository;
        this.tableService = tableService;
    }

    
    public Order createOrder(int tableId) {
        Table table = tableService.findTableById(tableId);
        
        if (table == null) {
            System.out.println("❌ Error: Table ID does not exist.");
            return null;
        }
        
        if (table.checkAvailability() == false) {
            System.out.println("❌ Error: Table is already occupied.");
            return null;
        }

       
        String orderId = "ORD" + String.format("%03d", orderCounter);
        orderCounter++;

        Order newOrder = new Order(orderId, table);
        orderRepository.save(newOrder); 
        return newOrder;
    }

    
    public Order getActiveOrderByTable(int tableId) {
        List<Order> allOrders = orderRepository.findAll();
        for (int i = 0; i < allOrders.size(); i++) {
            Order order = allOrders.get(i);
           
            if (order.getTable().getTableId() == tableId && order.isPaid() == false) {
                return order;
            }
        }
        return null; 
    }
}
