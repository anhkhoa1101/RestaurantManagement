package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * [Member 3] Kho lưu trữ đơn hàng — đọc/ghi từ file orders.txt
 *
 * Lưu ý: File chỉ lưu thông tin đơn giản (orderId, tableId, isPaid, tổng tiền).
 * Phần OrderDetail (danh sách món) được quản lý trong bộ nhớ vì phức tạp hơn.
 *
 * Định dạng mỗi dòng:
 *   orderId,tableId,isPaid,totalPrice
 * Ví dụ:
 *   ORD001,1,false,150000
 */
public class OrderRepository {

    private static final String FILE_PATH = "data/orders.txt";

    // Danh sách đơn hàng trong bộ nhớ (runtime)
    private List<Order> orders = new ArrayList<>();

    // Tự load khi khởi tạo
    public OrderRepository() {
        // Chú ý: không load từ file ở đây vì Order cần Table object đã dựng sẵn.
        // Việc load sẽ do OrderService xử lý khi cần thiết.
    }

    // ── CRUD trong bộ nhớ ───────────────────────────────

    /** Lấy tất cả đơn hàng đang có trong bộ nhớ */
    public List<Order> findAll() {
        return orders;
    }

    /** Tìm đơn hàng theo mã */
    public Order findById(String orderId) {
        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) return o;
        }
        return null;
    }

    /** Thêm đơn hàng mới */
    public void add(Order order) {
        orders.add(order);
        saveToFile(); // Đồng bộ ra file sau khi thêm
    }

    /** Xóa đơn hàng khỏi danh sách */
    public void remove(String orderId) {
        orders.removeIf(o -> o.getOrderId().equals(orderId));
        saveToFile();
    }

    /** Lưu lại (ví dụ sau khi cập nhật isPaid) */
    public void update() {
        saveToFile();
    }

    // ── Ghi ra file ─────────────────────────────────────
    public void saveToFile() {
        new File("data").mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Order o : orders) {
                // orderId,tableId,isPaid,totalPrice
                bw.write(o.getOrderId()
                        + "," + o.getTable().getTableId()
                        + "," + o.isPaid()
                        + "," + o.getTotalPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[OrderRepository] Lỗi ghi file: " + e.getMessage());
        }
    }
}