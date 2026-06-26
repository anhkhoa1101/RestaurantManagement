package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;
import com.mycompany.restaurantmanagement.model.Table;

import com.mycompany.restaurantmanagement.service.MenuService;
import com.mycompany.restaurantmanagement.service.OrderService;
import com.mycompany.restaurantmanagement.service.TableService;

import java.util.List;
import java.util.Scanner;

public class OrderUI {

    private final TableService tableService;
    private final OrderService orderService;
    private final MenuService menuService;
    private final Scanner sc;

    // ✅ Đã bỏ OrderDetailService — toàn bộ nghiệp vụ thêm/xóa món
    // do OrderService xử lý trực tiếp (xem lý do đã thống nhất ở trên)
    public OrderUI(TableService tableService, OrderService orderService, MenuService menuService) {

        this.tableService = tableService;
        this.orderService = orderService;
        this.menuService = menuService;
        this.sc = new Scanner(System.in);
    }

    public void showTableMap() {

        List<Table> tables = tableService.getAll();

        System.out.println("\n===== DANH SÁCH BÀN =====");

        for (Table table : tables) {
            System.out.println(table.getTableId() + " | " + table.getTableName() + " | " + table.getCapacity() + " người | " + (table.isOccupied() ? "Đang dùng" : "Trống"));
        }
    }

    public void createNewOrder() {

        try {
            showTableMap();

            System.out.print("Nhập ID bàn: ");
            int tableId = Integer.parseInt(sc.nextLine());

            Order order = orderService.createOrder(tableId);

            System.out.println("Tạo đơn thành công: " + order.getOrderId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * ✅ FIX: nhập theo mã bàn, không nhập mã đơn.
     * Vì 1 bàn có thể có nhiều order đang mở cùng lúc,
     * nếu có nhiều hơn 1 thì hiển thị danh sách để nhân viên chọn đúng order.
     */
    private Order resolveOrderByTable(int tableId) {

        List<Order> openOrders = orderService.getOpenOrdersByTable(tableId);

        if (openOrders.isEmpty()) {

            System.out.println("Bàn này không có đơn nào đang mở");

            return null;

        }

        // Chỉ lấy đơn đang mở gần nhất
        return openOrders.get(openOrders.size() - 1);
        }

        public void addOrRemoveItem () {

            try {
                showTableMap();

                System.out.print("Nhập ID bàn: ");
                int tableId = Integer.parseInt(sc.nextLine());

                Order order = resolveOrderByTable(tableId);

                if (order == null) {
                    return;
                }

                System.out.println("1. Thêm món");
                System.out.println("2. Xóa món");

                int choice = Integer.parseInt(sc.nextLine());

                System.out.print("Mã món: ");
                int itemId = Integer.parseInt(sc.nextLine());

                MenuItem item = menuService.getById(itemId);

                if (item == null) {
                    System.out.println("Không tìm thấy món ăn");
                    return;
                }

                if (choice == 1) {

                    System.out.print("Số lượng: ");
                    int qty = Integer.parseInt(sc.nextLine());

                    // ✅ Gọi trực tiếp OrderService, không qua OrderDetailService
                    orderService.addItemToOrder(order.getOrderId(), item, qty);

                } else if (choice == 2) {

                    orderService.removeItemFromOrder(order.getOrderId(), item);

                } else {
                    System.out.println("Lựa chọn không hợp lệ");
                    return;
                }

                System.out.println("Thành công");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        public void viewOrderDetail () {

            showTableMap();

            System.out.print("Nhập ID bàn: ");
            int tableId = Integer.parseInt(sc.nextLine());

            Order order = resolveOrderByTable(tableId);

            if (order == null) {
                return;
            }

            System.out.println("\n===== CHI TIẾT ĐƠN =====");

            for (OrderDetail detail : order.getDetails()) {
                System.out.println(detail);
            }

            System.out.println("Tổng tiền: " + order.getTotalPrice());
            System.out.println("Trạng thái: " + (order.isPaid() ? "Đã thanh toán" : "Chưa thanh toán"));
        }

        // ✅ confirmCheckout() đã bị xóa hoàn toàn — thuộc trách nhiệm Cashier (Module 4)

        public void run () {

            while (true) {

                System.out.println("\n===== ORDER MENU =====");
                System.out.println("1. Xem bàn");
                System.out.println("2. Tạo đơn");
                System.out.println("3. Thêm/Xóa món");
                System.out.println("4. Xem đơn");
                System.out.println("0. Thoát");

                try {

                    int choice = Integer.parseInt(sc.nextLine());

                    switch (choice) {

                        case 1:
                            showTableMap();
                            break;
                        case 2:
                            createNewOrder();
                            break;
                        case 3:
                            addOrRemoveItem();
                            break;
                        case 4:
                            viewOrderDetail();
                            break;
                        case 0: {
                            return;
                        }
                        default:
                            System.out.println("Lựa chọn không hợp lệ");
                    }

                } catch (Exception e) {
                    System.out.println("Dữ liệu không hợp lệ");
                }
            }
        }
    }