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
        this.totalPrice = 0;
        this.isPaid = false; // đơn mới tạo luôn ở trạng thái chưa thanh toán
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

    // Thêm món vào đơn. Nếu món này đã có trong đơn rồi thì chỉ tăng số lượng lên
    // (không tạo dòng OrderDetail mới trùng món)
    public void addDetail(MenuItem item, int qty) {
        for (OrderDetail d : details) {
            if (d.getMenuItem().getItemId() == item.getItemId()) {
                d.setQuantity(d.getQuantity() + qty);
                calculateTotal();
                return;
            }
        }
        details.add(new OrderDetail(item, qty));
        calculateTotal();
    }

    // Xoá hẳn 1 món khỏi đơn (xoá cả dòng, không phải giảm số lượng)
    public void removeDetail(MenuItem item) {
        details.removeIf(d -> d.getMenuItem().getItemId() == item.getItemId());
        calculateTotal();
    }

    // Tính lại tổng tiền đơn = tổng các subTotal của từng dòng chi tiết
    public double calculateTotal() {
        double sum = 0;
        for (OrderDetail d : details) {
            sum += d.getSubTotal();
        }
        this.totalPrice = sum;
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Đơn #" + orderId + " - Bàn: " + table.getTableName()
                + " - Tổng: " + totalPrice + " đ - "
                + (isPaid ? "Đã thanh toán" : "Chưa thanh toán");
    }
}