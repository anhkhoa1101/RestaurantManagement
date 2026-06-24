package com.mycompany.restaurantmanagement.model;

/**
 * [Member 3] Model - Một dòng chi tiết trong đơn hàng (1 món + số lượng).
 *
 * Lưu ý: orderPrice được "chốt" lại tại thời điểm gọi món, lấy từ
 * menuItem.getPrice() lúc đó. Việc này tránh trường hợp Member 2 đổi giá món
 * (MenuItem.setPrice) SAU khi khách đã gọi món, làm sai lệch số tiền đơn cũ.
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
        if (q <= 0) {
            throw new IllegalArgumentException("Số lượng phải > 0");
        }
        this.quantity = q;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public double getSubTotal() {
        return orderPrice * quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "menuItem=" + menuItem.getName() +
                ", quantity=" + quantity +
                ", orderPrice=" + orderPrice +
                ", subTotal=" + getSubTotal() +
                '}';
    }
}