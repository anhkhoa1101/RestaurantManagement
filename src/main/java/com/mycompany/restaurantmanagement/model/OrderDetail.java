package com.mycompany.restaurantmanagement.model;

/**
 * [Member 3] Một dòng trong đơn hàng — tương ứng với 1 món ăn + số lượng.
 * Ví dụ: "Phở bò x2 = 70.000đ"
 *
 * Require từ Member 2: MenuItem (món ăn từ thực đơn)
 */
public class OrderDetail {

    private MenuItem menuItem;   // Món ăn (lấy từ thực đơn của Member 2)
    private int quantity;        // Số lượng gọi
    private double orderPrice;   // Giá tại thời điểm gọi (snapshot, không đổi dù thực đơn thay giá)

    // Constructor
    public OrderDetail(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.orderPrice = menuItem.getPrice(); // Chốt giá ngay khi gọi món
    }

    // ── Getter ──────────────────────────────────────────
    public MenuItem getMenuItem()  { return menuItem; }
    public int getQuantity()       { return quantity; }
    public double getOrderPrice()  { return orderPrice; }

    /**
     * Cập nhật số lượng (dùng khi chỉnh sửa đơn).
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Tính thành tiền của dòng này: đơn giá × số lượng.
     */
    public double getSubTotal() {
        return orderPrice * quantity;
    }

    @Override
    public String toString() {
        return String.format("  %-25s x%d  %,.0f đ",
                menuItem.getName(), quantity, getSubTotal());
    }
}