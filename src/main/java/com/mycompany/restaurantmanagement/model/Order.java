/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.restaurantmanagement.model;

/**
 *
 * @author khoa0
 */


import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;              
    private Table table;                 
    private List<OrderDetail> details;
    private double totalPrice;           
    private boolean isPaid;              

    
    public Order(String orderId, Table table) {
        this.orderId = orderId;
        this.table = table;
        this.details = new ArrayList<>(); 
        this.totalPrice = 0.0;
        this.isPaid = false;             
        
        
        this.table.setOccupied(true);
    }

    
    public String getOrderId() {
        return this.orderId;
    }

    public Table getTable() {
        return this.table;
    }

    public List<OrderDetail> getDetails() {
        return this.details;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public boolean isPaid() {
        return this.isPaid;
    }


    public void setPaid(boolean paid) {
        this.isPaid = paid;
        if (paid == true) {
            this.table.setOccupied(false); 
        }
    }

    
    public double calculateTotal() {
        this.totalPrice = 0.0; 
        for (int i = 0; i < details.size(); i++) {
            OrderDetail detail = details.get(i);
            this.totalPrice += detail.getSubTotal();
        }
        return this.totalPrice;
    }

    
    public void addDetail(MenuItem item, int qty) {
        if (this.isPaid == true) {
            System.out.println("❌ Lỗi: Đơn hàng này đã thanh toán xong, không thể thêm món!");
            return;
        }

        
        for (int i = 0; i < details.size(); i++) {
            OrderDetail detail = details.get(i);
            
            
            if (detail.getMenuItem().getItemId() == item.getItemId()) {
                int oldQuantity = detail.getQuantity();
                detail.setQuantity(oldQuantity + qty); 
                calculateTotal(); 
                return; 
            }
        }

        
        this.details.add(new OrderDetail(item, qty));
        calculateTotal(); 
    }

    
    public void removeDetail(MenuItem item) {
        if (this.isPaid == true) {
            System.out.println("❌ Lỗi: Đơn hàng này đã thanh toán xong, không thể xóa món!");
            return;
        }

        
        for (int i = 0; i < details.size(); i++) {
            OrderDetail currentDetail = details.get(i);
            
            if (currentDetail.getMenuItem().getItemId() == item.getItemId()) {
                this.details.remove(i); 
                calculateTotal(); // 
                System.out.println("✅ Đã hủy món: " + item.getName());
                return;
            }
        }
        System.out.println("❌ Không tìm thấy món này trong hóa đơn của bàn.");
    }

    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("=========================================\n");
    sb.append("ORDER ID: ").append(orderId).append(" | ").append(table.getTableName()).append("\n");
    sb.append("Status: ").append(isPaid ? "Paid" : "Unpaid").append("\n");
    sb.append("-----------------------------------------\n");
    
    for (OrderDetail detail : details) {
        sb.append(detail.toString()).append("\n");
    }
    
    sb.append("-----------------------------------------\n");
    sb.append(String.format("TOTAL AMOUNT: %,.0f VND\n", calculateTotal()));
    sb.append("=========================================");
    return sb.toString();
}
}
