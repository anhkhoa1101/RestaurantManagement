package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Manager;
import com.mycompany.restaurantmanagement.model.Role;
import com.mycompany.restaurantmanagement.model.User;
import com.mycompany.restaurantmanagement.service.UserService;
import java.util.Scanner;

public class UIManager {
    private Manager manager;
    private UserService userService;
    private Scanner sc = new Scanner(System.in);

    public UIManager(Manager manager, UserService userService) {
        this.manager = manager;
        this.userService = userService;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n===== MENU MANAGER (" + manager.getFullName() + ") =====");
            System.out.println("1. Quản lý tài khoản nhân viên");
            System.out.println("2. Xem báo cáo tổng");
            System.out.println("3. Đăng xuất");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    manageUsers();
                    break;
                case "2":
                    System.out.println("-> Gọi ReportService (Module 4) để xem báo cáo.");
                    break;
                case "3":
                    System.out.println("Đăng xuất...");
                    running = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private void manageUsers() {

    boolean back = false;

    while (!back) {

        System.out.println("\n===== QUẢN LÝ TÀI KHOẢN =====");
        System.out.println("1. Thêm tài khoản");
        System.out.println("2. Sửa tài khoản");
        System.out.println("3. Xóa tài khoản");
        System.out.println("4. Xem danh sách");
        System.out.println("5. Quay lại");

        System.out.print("Chọn: ");
        String choice = sc.nextLine();

        switch (choice) {

            case "1":
                addUser();
                break;

            case "2":
                editUser();
                break;

            case "3":
                deleteUser();
                break;

            case "4":
                showUsers();
                break;

            case "5":
                back = true;
                break;

            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
}
   private void addUser() {

    System.out.print("ID: ");
    int id = Integer.parseInt(sc.nextLine());

    System.out.print("Username: ");
    String username = sc.nextLine();

    System.out.print("Password: ");
    String password = sc.nextLine();

    System.out.print("Họ tên: ");
    String fullName = sc.nextLine();

    System.out.print("Email: ");
    String email = sc.nextLine();

    System.out.println("Role:");
    System.out.println("1. Manager");
    System.out.println("2. Employee");
    System.out.println("3. Cashier");

    int roleChoice = Integer.parseInt(sc.nextLine());

    Role role;

    switch (roleChoice) {

        case 1:
            role = Role.MANAGER;
            break;

        case 2:
            role = Role.EMPLOYEE;
            break;

        case 3:
            role = Role.CASHIER;
            break;

        default:
            System.out.println("Role không hợp lệ!");
            return;
    }

    userService.createUser(
            id,
            username,
            password,
            fullName,
            email,
            role
    );
}
   private void editUser() {

    System.out.print("Nhập ID cần sửa: ");
    int id = Integer.parseInt(sc.nextLine());

    System.out.print("Họ tên mới: ");
    String fullName = sc.nextLine();

    System.out.print("Email mới: ");
    String email = sc.nextLine();

    userService.updateUserInfo(
            id,
            fullName,
            email
    );
}
    private void deleteUser() {

    System.out.print("Nhập ID cần xóa: ");
    int id = Integer.parseInt(sc.nextLine());

    userService.deleteUser(id);
}
    private void showUsers() {

    System.out.println("\n===== DANH SÁCH TÀI KHOẢN =====");

    for (User user : userService.getAllUsers()) {

        System.out.println("--------------------------");
        System.out.println("ID: " + user.getUserId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Họ tên: " + user.getFullName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole());
    }
}
}