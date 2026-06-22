package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;

/**
 * [Member 3] Xử lý logic cho từng dòng trong đơn hàng.
 * Các thao tác: xem chi tiết, tính tổng, in đơn.
 *
 * Lưu ý: việc thêm/xóa món được xử lý trong OrderService.
 * OrderDetailService chỉ lo phần hiển thị và tính toán.
 */
public class OrderDetailService {

    /**
     * In chi tiết toàn bộ đơn hàng ra console.
     */
    public void printOrderDetails(Order order) {
        System.out.println(order.toString());
    }

    /**
     * Tính và in tổng tiền hiện tại của đơn.
     */
    public void printTotal(Order order) {
        double total = order.calculateTotal();
        System.out.printf("Tổng tiền đơn %s: %,.0f đ%n", order.getOrderId(), total);
    }

    /**
     * Kiểm tra đơn có rỗng không (chưa có món nào).
     */
    public boolean isEmpty(Order order) {
        return order.getDetails().isEmpty();
    }

    /**
     * Đếm số dòng (số loại món) trong đơn.
     */
    public int countItems(Order order) {
        return order.getDetails().size();
    }

    /**
     * Tính tổng số lượng tất cả các món trong đơn.
     * Ví dụ: Phở x2 + Cơm x3 = 5
     */
    public int totalQuantity(Order order) {
        int sum = 0;
        for (OrderDetail d : order.getDetails()) {
            sum += d.getQuantity();
        }
        return sum;
    }
}