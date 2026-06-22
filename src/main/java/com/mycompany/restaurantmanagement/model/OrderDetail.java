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
        this.orderPrice = menuItem.getPrice(); // chốt giá ngay lúc gọi món
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    // Tiền của riêng dòng món này = đơn giá (đã chốt) * số lượng
    public double getSubTotal() {
        return orderPrice * quantity;
    }

    @Override
    public String toString() {
        return "  - " + menuItem.getName() + " x" + quantity + " = " + getSubTotal() + " đ";
    }
}