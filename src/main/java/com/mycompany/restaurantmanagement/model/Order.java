package com.mycompany.restaurantmanagement.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order {

    private String orderId;

    private Table table;

    private List<OrderDetail> details;

    private Date createdAt;

    private double totalPrice;

    private boolean isPaid;

    public Order() {

        this.details = new ArrayList<>();

        this.createdAt = new Date();

    }

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

    public void setTable(Table table) {

        this.table = table;

    }

    public List<OrderDetail> getDetails() {

        return new ArrayList<>(details);

    }

    public Date getCreatedAt() {

        return createdAt;

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

    private void validateEditable() {

        if (isPaid) {

            throw new IllegalStateException("Đơn đã thanh toán");

        }

    }

    public void addDetail(MenuItem item, int qty) {

        validateEditable();

        if (qty <= 0) {

            throw new IllegalArgumentException("Số lượng phải > 0");

        }

        for (OrderDetail detail : details) {

            if (detail.getMenuItem().getItemId() == item.getItemId()) {

                detail.setQuantity(detail.getQuantity() + qty);

                calculateTotal();

                return;

            }

        }

        details.add(new OrderDetail(item, qty));

        calculateTotal();

    }

    public void removeDetail(MenuItem item) {

        validateEditable();

        details.removeIf(detail ->

                detail.getMenuItem().getItemId()

                        ==

                        item.getItemId());

        calculateTotal();

    }

    public void updateDetailQuantity(MenuItem item, int qty) {

        validateEditable();

        if (qty <= 0) {

            removeDetail(item);

            return;

        }

        for (OrderDetail detail : details) {

            if (detail.getMenuItem().getItemId() == item.getItemId()) {

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
    public boolean equals(Object obj) {

        if (this == obj) {

            return true;

        }

        if (obj == null || getClass() != obj.getClass()) {

            return false;

        }

        Order order = (Order) obj;

        return Objects.equals(orderId, order.orderId);

    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId);

    }

    @Override
    public String toString() {

        return "Order{" + "orderId='" + orderId + '\'' + ", table=" + (table == null ? "null" : table.getTableName()) + ", createdAt=" + createdAt + ", totalPrice=" + totalPrice + ", isPaid=" + isPaid + ", details=" + details + '}';

    }

}