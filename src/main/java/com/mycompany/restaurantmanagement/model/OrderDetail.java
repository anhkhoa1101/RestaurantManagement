/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.model;

/**
 *
 * @author khoa0
 */


public class OrderDetail {
    private MenuItem menuItem; 
    private int quantity;
    private double orderPrice;

    public OrderDetail(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.orderPrice = menuItem.getPrice(); 
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        this.quantity = q;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public double getSubTotal() {
        return this.quantity * this.orderPrice;
    }

    @Override
    public String toString() {
        return String.format("%-20s x%-5d Price: %,.0f VNĐ | Subtotal: %,.0f VNĐ", 
                menuItem.getName(), quantity, orderPrice, getSubTotal());
    }
}
