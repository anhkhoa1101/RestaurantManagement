package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.service.OrderDetailService;
import com.mycompany.restaurantmanagement.service.OrderService;
import com.mycompany.restaurantmanagement.service.TableService;

import java.util.List;
import java.util.Scanner;

/**
 * [Member 3] Giao diện console để nhân viên tương tác với hệ thống đặt bàn / gọi món.
 *
 * Require từ Member 2:
 *   - MenuService (Member 2): để lấy danh sách món ăn cho nhân viên chọn
 *
 * Luồng sử dụng điển hình:
 *   1. Xem sơ đồ bàn
 *   2. Chọn bàn → tạo đơn
 *   3. Thêm món vào đơn (lấy từ thực đơn của Member 2)
 *   4. Xem lại đơn
 *   5. Xác nhận đơn → trừ kho (Member 2)
 */
public class OrderUI {

    private TableService tableService;
    private OrderService orderService;
    private OrderDetailService orderDetailService;

    // MenuServiceInterface: Member 2 cung cấp để lấy danh sách món ăn
    private MenuServiceInterface menuService;

    private Scanner scanner = new Scanner(System.in);

    public OrderUI(TableService tableService,
                   OrderService orderService,
                   OrderDetailService orderDetailService,
                   MenuServiceInterface menuService) {
        this.tableService = tableService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.menuService = menuService;
    }

    /**
     * Hiển thị menu chính của Module 3 và xử lý input người dùng.
     */
    public void showMenu() {
        int choice;
        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║   MODULE 3 — QUẢN LÝ ĐƠN HÀNG   ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║ 1. Xem sơ đồ bàn                ║");
            System.out.println("║ 2. Tạo đơn hàng mới             ║");
            System.out.println("║ 3. Thêm món vào đơn             ║");
            System.out.println("║ 4. Xóa món khỏi đơn             ║");
            System.out.println("║ 5. Xem chi tiết đơn             ║");
            System.out.println("║ 6. Xác nhận đơn (trừ kho)       ║");
            System.out.println("║ 7. Hủy đơn hàng                 ║");
            System.out.println("║ 0. Quay lại                     ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Chọn: ");
            choice = readInt();

            switch (choice) {
                case 1:  tableService.printTableMap();
                case 2:  handleCreateOrder();
                case 3:  handleAddItem();
                case 4:  handleRemoveItem();
                case 5:  handleViewOrder();
                case 6:  handleConfirmOrder();
                case 7:  handleCancelOrder();
                case 0:  System.out.println("Quay lại menu chính...");
                default: System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    // ── Xử lý từng chức năng ─────────────────────────────

    /** Chức năng 2: Tạo đơn hàng mới */
    private void handleCreateOrder() {
        tableService.printTableMap(); // Hiển thị sơ đồ bàn để chọn
        System.out.print("Nhập ID bàn muốn đặt: ");
        int tableId = readInt();
        orderService.createOrder(tableId);
    }

    /** Chức năng 3: Thêm món vào đơn */
    private void handleAddItem() {
        System.out.print("Nhập mã đơn hàng (ví dụ: ORD001): ");
        String orderId = scanner.nextLine().trim();

        // Hiển thị thực đơn từ Member 2 để nhân viên chọn
        List<MenuItem> menu = menuService.getAvailableMenuItems();
        if (menu.isEmpty()) {
            System.out.println("Không có món nào trong thực đơn.");
            return;
        }

        System.out.println("\n── THỰC ĐƠN ─────────────────────");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.printf("  %d. %-25s %,.0f đ%n",
                    i + 1, item.getName(), item.getPrice());
        }
        System.out.println("──────────────────────────────────");

        System.out.print("Chọn số thứ tự món: ");
        int idx = readInt() - 1; // Chuyển từ 1-based sang 0-based

        if (idx < 0 || idx >= menu.size()) {
            System.out.println("Số thứ tự không hợp lệ.");
            return;
        }

        System.out.print("Số lượng: ");
        int qty = readInt();
        if (qty <= 0) {
            System.out.println("Số lượng phải lớn hơn 0.");
            return;
        }

        // Thêm món vào đơn (OrderService sẽ xử lý logic)
        orderService.addItemToOrder(orderId, menu.get(idx), qty);
    }

    /** Chức năng 4: Xóa món khỏi đơn */
    private void handleRemoveItem() {
        System.out.print("Nhập mã đơn hàng: ");
        String orderId = scanner.nextLine().trim();

        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            System.out.println("Không tìm thấy đơn " + orderId);
            return;
        }

        // Hiển thị các món đang có trong đơn
        System.out.println("\n── MÓN ĐANG TRONG ĐƠN ──");
        List<com.restaurant.model.OrderDetail> details = order.getDetails();
        for (int i = 0; i < details.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, details.get(i));
        }

        System.out.print("Chọn số thứ tự món cần xóa: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= details.size()) {
            System.out.println("Không hợp lệ.");
            return;
        }

        // Xóa món đã chọn
        MenuItem item = details.get(idx).getMenuItem();
        orderService.removeItemFromOrder(orderId, item);
    }

    /** Chức năng 5: Xem chi tiết đơn */
    private void handleViewOrder() {
        System.out.print("Nhập mã đơn hàng: ");
        String orderId = scanner.nextLine().trim();

        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            System.out.println("Không tìm thấy đơn " + orderId);
            return;
        }
        orderDetailService.printOrderDetails(order);
    }

    /** Chức năng 6: Xác nhận đơn → trừ kho của Member 2 */
    private void handleConfirmOrder() {
        System.out.print("Nhập mã đơn hàng cần xác nhận: ");
        String orderId = scanner.nextLine().trim();
        orderService.confirmOrder(orderId); // OrderService sẽ gọi InventoryService để trừ kho
    }

    /** Chức năng 7: Hủy đơn */
    private void handleCancelOrder() {
        System.out.print("Nhập mã đơn hàng cần hủy: ");
        String orderId = scanner.nextLine().trim();
        System.out.print("Bạn chắc chắn muốn hủy đơn " + orderId + "? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if ("y".equalsIgnoreCase(confirm)) {
            orderService.cancelOrder(orderId);
        } else {
            System.out.println("Đã hủy thao tác.");
        }
    }

    // ── Tiện ích ─────────────────────────────────────────

    /** Đọc số nguyên an toàn, không crash khi nhập sai */
    private int readInt() {
        try {
            int val = Integer.parseInt(scanner.nextLine().trim());
            return val;
        } catch (NumberFormatException e) {
            return -1; // Trả về -1 nếu nhập không phải số
        }
    }

    // ── Interface kết nối với Member 2's MenuService ─────
    /**
     * Member 2 sẽ implement interface này để cung cấp danh sách món ăn.
     *
     * Cách dùng:
     *   MenuService menuSvc = new MenuService(...); // Member 2's class
     *   OrderUI ui = new OrderUI(tableSvc, orderSvc, detailSvc, menuSvc);
     */
    public interface MenuServiceInterface {
        /**
         * Lấy danh sách các món ăn đang phục vụ (isAvailable = true).
         * @return List<MenuItem>
         */
        List<MenuItem> getAvailableMenuItems();
    }
}