package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;
import com.mycompany.restaurantmanagement.service.MenuService;
import com.mycompany.restaurantmanagement.service.OrderDetailService;
import com.mycompany.restaurantmanagement.service.OrderService;

import java.util.List;
import java.util.Scanner;

/**
 * [Member 3]
 * Console UI quản lý đơn hàng.
 *
 * Chức năng:
 * - Tạo đơn
 * - Gọi món
 * - Xóa món
 * - Cập nhật số lượng
 * - Xem danh sách đơn
 * - Xem chi tiết đơn
 * - Đóng đơn
 * - Hủy đơn
 */
public class OrderUI {

    // Service xử lý nghiệp vụ Order
    private OrderService orderService;

    // Service xử lý món trong đơn
    private OrderDetailService orderDetailService;

    // Service đọc menu
    private MenuService menuService;

    // Nhập dữ liệu từ bàn phím
    private Scanner scanner;

    // Constructor
    public OrderUI(
            OrderService orderService,
            OrderDetailService orderDetailService,
            MenuService menuService
    ) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.menuService = menuService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Chạy giao diện chính
     */
    public void start() {

        while (true) {

            show();

            int choice =
                    Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    createOrder();
                    break;

                case 2:
                    addItem();
                    break;

                case 3:
                    removeItem();
                    break;

                case 4:
                    updateQuantity();
                    break;

                case 5:
                    viewOrders();
                    break;

                case 6:
                    viewOrderDetail();
                    break;

                case 7:
                    closeOrder();
                    break;

                case 8:
                    cancelOrder();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    /**
     * Hiển thị menu chức năng
     */
    public void show() {

        System.out.println("\n===== ORDER MANAGEMENT =====");

        System.out.println("1. Create Order");

        System.out.println("2. Add Item");

        System.out.println("3. Remove Item");

        System.out.println("4. Update Quantity");

        System.out.println("5. View Orders");

        System.out.println("6. View Order Detail");

        System.out.println("7. Close Order");

        System.out.println("8. Cancel Order");

        System.out.println("0. Exit");

        System.out.print("Choice: ");
    }

    /**
     * Tạo đơn mới
     */
    public void createOrder() {

        System.out.print("Table ID: ");

        int tableId =
                Integer.parseInt(scanner.nextLine());

        Order order =
                orderService.createOrder(tableId);

        if (order == null)
            System.out.println("Create order failed");

        else
            System.out.println(order);
    }

    /**
     * Thêm món vào đơn
     */
    public void addItem() {

        System.out.print("Order ID: ");

        String orderId =
                scanner.nextLine();

        System.out.print("Menu Item ID: ");

        int itemId =
                Integer.parseInt(scanner.nextLine());

        System.out.print("Quantity: ");

        int qty =
                Integer.parseInt(scanner.nextLine());

        boolean success =
                orderDetailService.addItemToOrder(
                        orderId,
                        itemId,
                        qty
                );

        System.out.println(success ? "Add success" : "Add failed");
    }

    /**
     * Xóa món khỏi đơn
     */
    public void removeItem() {

        System.out.print("Order ID: ");

        String orderId =
                scanner.nextLine();

        System.out.print("Menu Item ID: ");

        int itemId =
                Integer.parseInt(scanner.nextLine());

        boolean success =
                orderDetailService.removeItemFromOrder(
                        orderId,
                        itemId
                );

        System.out.println(success ? "Remove success" : "Remove failed");
    }

    /**
     * Đổi số lượng món
     */
    public void updateQuantity() {

        System.out.print("Order ID: ");

        String orderId =
                scanner.nextLine();

        System.out.print("Menu Item ID: ");

        int itemId =
                Integer.parseInt(scanner.nextLine());

        System.out.print("New Quantity: ");

        int qty =
                Integer.parseInt(scanner.nextLine());

        boolean success =
                orderDetailService.updateItemQuantity(
                        orderId,
                        itemId,
                        qty
                );

        System.out.println(
                success
                        ? "Update success"
                        : "Update failed"
        );
    }

    /**
     * Xem toàn bộ đơn
     */
    public void viewOrders() {

        List<Order> orders =
                orderService.getAllOrders();

        for (Order o : orders)
            System.out.println(o);
    }

    /**
     * Xem chi tiết đơn
     */
    public void viewOrderDetail() {

        System.out.print("Order ID: ");

        String orderId =
                scanner.nextLine();

        List<OrderDetail> details =
                orderDetailService.getOrderDetails(orderId);

        for (OrderDetail d : details)
            System.out.println(d);
    }

    /**
     * Thanh toán đơn
     */
    public void closeOrder() {

        System.out.print("Order ID: ");

        String orderId =
                scanner.nextLine();

        boolean success =
                orderService.closeOrder(orderId);

        System.out.println(
                success
                        ? "Closed"
                        : "Close failed"
        );
    }

    /**
     * Hủy đơn
     */
    public void cancelOrder() {

        System.out.print("Order ID: ");

        String orderId =
                scanner.nextLine();

        boolean success =
                orderService.cancelOrder(orderId);

        System.out.println(
                success
                        ? "Cancelled"
                        : "Cancel failed"
        );
    }
}