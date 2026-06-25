package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.MenuItem;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.OrderDetail;
import com.mycompany.restaurantmanagement.model.Table;

import com.mycompany.restaurantmanagement.service.OrderDetailService;
import com.mycompany.restaurantmanagement.service.OrderService;
import com.mycompany.restaurantmanagement.service.TableService;

import java.util.List;
import java.util.Scanner;

public class OrderUI {

    private final TableService tableService;

    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    private final Scanner sc;

    public OrderUI(TableService tableService, OrderService orderService, OrderDetailService orderDetailService) {

        this.tableService = tableService;

        this.orderService = orderService;

        this.orderDetailService = orderDetailService;

        this.sc = new Scanner(System.in);

    }

    public void showTableMap() {

        List<Table> tables = tableService.getAll();

        System.out.println("\n===== DANH SÁCH BÀN =====");

        for (Table table : tables) {

            System.out.println(

                    table.getTableId() + " | " + table.getTableName() + " | " + table.getCapacity() + " người | " + (table.isOccupied() ? "Đang dùng" : "Trống")

            );

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

    public void addOrRemoveItem() {

        try {

            System.out.print("Nhập mã đơn: ");

            String orderId = sc.nextLine();

            Order order = orderService.getById(orderId);

            if (order == null) {

                System.out.println("Không tìm thấy đơn");

                return;

            }

            System.out.println("1. Thêm món");

            System.out.println("2. Xóa món");

            int choice = Integer.parseInt(sc.nextLine());

            System.out.print("Mã món: ");

            int itemId = Integer.parseInt(sc.nextLine());

            MenuItem item = new MenuItem(itemId, "", "", 0, null);

            if (choice == 1) {

                System.out.print("Số lượng: ");

                int qty = Integer.parseInt(sc.nextLine());

                orderDetailService.addDetail(order, item, qty);

            } else if (choice == 2) {

                orderDetailService.removeDetail(order, item);

            } else {

                System.out.println("Lựa chọn không hợp lệ");

                return;

            }

            System.out.println("Thành công");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void viewOrderDetail() {

        System.out.print("Nhập mã đơn: ");

        String id = sc.nextLine();

        Order order = orderService.getById(id);

        if (order == null) {

            System.out.println("Không tìm thấy đơn");

            return;

        }

        System.out.println("\n===== CHI TIẾT ĐƠN =====");

        for (OrderDetail detail : order.getDetails()) {

            System.out.println(detail);

        }

        System.out.println("Tổng tiền: " + order.getTotalPrice());

        System.out.println("Trạng thái: " + (order.isPaid() ? "Đã thanh toán" : "Chưa thanh toán"));

    }

    public void confirmCheckout() {

        try {

            System.out.print("Nhập mã đơn: ");

            String id = sc.nextLine();

            Order order = orderService.checkoutOrder(id);

            System.out.println("Thanh toán thành công");

            System.out.println("Tổng tiền: " + order.getTotalPrice());

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void run() {

        while (true) {

            System.out.println("\n===== ORDER MENU =====");

            System.out.println("1. Xem bàn");

            System.out.println("2. Tạo đơn");

            System.out.println("3. Thêm/Xóa món");

            System.out.println("4. Xem đơn");

            System.out.println("5. Thanh toán");

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

                    case 5:

                        confirmCheckout();

                        break;

                    case 0:

                        return;

                    default:

                        System.out.println("Lựa chọn không hợp lệ");

                }

            } catch (Exception e) {

                System.out.println("Dữ liệu không hợp lệ");

            }

        }

    }

}