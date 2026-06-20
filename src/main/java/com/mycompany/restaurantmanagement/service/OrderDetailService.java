package com.mycompany.restaurantmanagement.service;



import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.MenuItem;

public class OrderDetailService {
    private OrderService orderService; 

    public OrderDetailService(OrderService orderService) {
        this.orderService = orderService;
    }

    
    public void addItemToTable(int tableId, MenuItem item, int quantity) {
        Order activeOrder = orderService.getActiveOrderByTable(tableId);
        
        if (activeOrder == null) {
            System.out.println("❌ Error: Cannot add items. This table has no active order.");
            return;
        }
        
        
        activeOrder.addDetail(item, quantity);
    }

    
    public void removeItemFromTable(int tableId, MenuItem item) {
        Order activeOrder = orderService.getActiveOrderByTable(tableId);
        
        if (activeOrder == null) {
            System.out.println("❌ Error: Cannot remove items. This table has no active order.");
            return;
        }
        
       
        activeOrder.removeDetail(item);
    }
}
