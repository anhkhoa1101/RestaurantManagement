package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Manager;
import java.util.Scanner;

public class UIManager {
    private Manager manager;
    private Scanner sc = new Scanner(System.in);

    public UIManager(Manager manager) {
        this.manager = manager;
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
                    System.out.println("-> Gọi UserService để quản lý tài khoản (thêm/sửa/xoá).");
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
}