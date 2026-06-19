/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.ui;

/**
 *
 * @author khoa0
 */
import com.mycompany.restaurantmanagement.model.User;
import com.mycompany.restaurantmanagement.service.UserService;
import java.util.Scanner;

public class UserUI {
    private UserService userService;
    private Scanner scanner;

    public UserUI(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    // Giao diện Đăng nhập
    public User showLogin() {
        System.out.println("\n=== HỆ THỐNG QUẢN LÝ NHÀ HÀNG ===");
        System.out.println("Vui lòng đăng nhập để tiếp tục.");

        System.out.print("Tên đăng nhập: ");
        String username = scanner.nextLine();

        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        return userService.login(username, password);
    }

    // Menu quản lý dành riêng cho Manager
    public void showManagerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- 👥 QUẢN LÝ NHÂN SỰ (MANAGER) ---");
            System.out.println("1. Xem danh sách nhân viên");
            System.out.println("2. Thêm nhân viên mới");
            System.out.println("3. Khóa/Mở khóa tài khoản nhân viên");
            System.out.println("0. Quay lại menu chính");
            System.out.print("Chọn chức năng: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n[Danh sách nhân viên sẽ hiển thị ở đây]");
                    // Gọi ManagerService.viewAllEmployees()
                    break;
                case "2":
                    System.out.println("\n[Tính năng thêm nhân viên]");
                    // Nhận input từ Scanner và gọi ManagerService.addEmployee()
                    break;
                case "3":
                    System.out.print("Nhập ID nhân viên cần thay đổi trạng thái: ");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Chọn trạng thái (1: Mở khóa, 0: Khóa): ");
                        boolean status = scanner.nextLine().equals("1");
                        userService.toggleAccountStatus(id, status);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Lỗi: ID phải là số nguyên!");
                    }
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
}
