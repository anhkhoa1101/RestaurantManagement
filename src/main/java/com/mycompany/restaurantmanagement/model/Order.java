package com.mycompany.restaurantmanagement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * [Member 3] Đơn hàng gắn với một bàn ăn.
 * Chứa danh sách các món đã gọi (OrderDetail) và tổng tiền.
 *
 * Require từ Member 2: MenuItem (để thêm món vào đơn)
 */
public class Order {

    private String orderId;              // Mã đơn hàng (ví dụ: "ORD001")
    private Table table;                 // Bàn ăn đặt đơn này
    private List<OrderDetail> details;   // Danh sách các món đã gọi
    private double totalPrice;           // Tổng tiền (tự tính)
    private boolean isPaid;             // Trạng thái thanh toán

    // Constructor
    public Order(String orderId, Table table) {
        this.orderId = orderId;
        this.table = table;
        this.details = new ArrayList<>();
        this.totalPrice = 0;
        this.isPaid = false; // Đơn mới chưa thanh toán
    }

    // ── Getter ──────────────────────────────────────────
    public String getOrderId()          { return orderId; }
    public Table getTable()             { return table; }
    public List<OrderDetail> getDetails() { return details; }
    public double getTotalPrice()       { return totalPrice; }
    public boolean isPaid()             { return isPaid; }

    /**
     * Đánh dấu đã thanh toán (Member 4 gọi sau khi thu tiền).
     */
    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }

    /**
     * Thêm một món vào đơn.
     * Nếu món đã có trong đơn → tăng số lượng lên.
     * Sau đó tự tính lại tổng tiền.
     *
     * @param item MenuItem từ thực đơn (Member 2)
     * @param qty  Số lượng muốn thêm
     */
    public void addDetail(MenuItem item, int qty) {
        // Kiểm tra xem món này đã có trong đơn chưa
        for (OrderDetail d : details) {
            if (d.getMenuItem().getItemId() == item.getItemId()) {
                // Đã có → chỉ cộng thêm số lượng
                d.setQuantity(d.getQuantity() + qty);
                calculateTotal();
                return;
            }
        }
        // Chưa có → thêm dòng mới
        details.add(new OrderDetail(item, qty));
        calculateTotal();
    }

    /**
     * Xóa một món khỏi đơn theo MenuItem.
     */
    public void removeDetail(MenuItem item) {
        details.removeIf(d -> d.getMenuItem().getItemId() == item.getItemId());
        calculateTotal();
    }

    /**
     * Tính tổng tiền bằng cách cộng thành tiền của từng dòng.
     * Được gọi tự động sau mỗi thao tác thêm/xóa món.
     */
    public double calculateTotal() {
        totalPrice = 0;
        for (OrderDetail d : details) {
            totalPrice += d.getSubTotal();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ĐƠN HÀNG ").append(orderId)
          .append(" | ").append(table.getTableName())
          .append(isPaid ? " [ĐÃ THANH TOÁN]" : " [ĐANG MỞ]")
          .append(" ===\n");

        if (details.isEmpty()) {
            sb.append("  (Chưa có món nào)\n");
        } else {
            for (OrderDetail d : details) {
                sb.append(d.toString()).append("\n");
            }
        }
        sb.append(String.format("  TỔNG: %,.0f đ", totalPrice));
        return sb.toString();
    }
}