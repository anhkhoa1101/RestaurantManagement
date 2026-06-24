package com.mycompany.restaurantmanagement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * [Member 3] Model - Đơn hàng gắn với 1 bàn, chứa danh sách OrderDetail.
 *
 * isPaid:
 *   - false -> đơn đang mở, nhân viên còn được thêm/xoá món
 *   - true  -> đơn đã thanh toán (do Member 4 cập nhật), bị khoá, không sửa được nữa
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private String orderId;
    private Table table;
    private List<OrderDetail> details;
    private Date createdAt;
    private double totalPrice;
    private boolean isPaid;

    public Order(String orderId, Table table) {
        this.orderId = orderId;
        this.table = table;
        this.details = new ArrayList<>();
        this.createdAt = new Date();
        this.totalPrice = 0;
        this.isPaid = false;
    }

    public String getOrderId() {
        return orderId;
    }

    public Table getTable() {
        return table;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }

    public void addDetail(MenuItem item, int qty) {

        for (OrderDetail detail : details) {
            if (detail.getMenuItem().getItemId() == item.getItemId() ) {
                detail.setQuantity(detail.getQuantity() + qty);
                calculateTotal();
                return;
            }
        }

        details.add(new OrderDetail(item, qty));
        calculateTotal();
    }

    public void removeDetail(MenuItem item) {
        details.removeIf(detail ->
                detail.getMenuItem().getItemId() == item.getItemId() );

        calculateTotal();
    }

    public void updateDetailQuantity(MenuItem item, int qty) {

        for (OrderDetail detail : details) {

            if (detail.getMenuItem().getItemId() == item.getItemId() ) {
                detail.setQuantity(qty);
                break;
            }
        }

        calculateTotal();
    }

    public double calculateTotal() {

        totalPrice = 0;

        for (OrderDetail detail : details) {
            totalPrice += detail.getSubTotal();
        }

        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", table=" + table.getTableName() +
                ", createdAt=" + createdAt +
                ", totalPrice=" + totalPrice +
                ", isPaid=" + isPaid +
                ", details=" + details +
                '}';
    }
}