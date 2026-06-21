package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.User;
import com.mycompany.restaurantmanagement.service.AuthService;
import java.util.Scanner;

public class LoginUI {

        private AuthService authService;
        private Router router;
        private Scanner sc = new Scanner(System.in);

        public LoginUI(AuthService authService, Router router) {
            this.authService = authService;
            this.router = router;
        }

        public void show() {
            System.out.println("========== ĐĂNG NHẬP HỆ THỐNG ==========");
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            User user = authService.authenticate(username, password);
            if (user == null) {
                System.out.println("❌ Sai tài khoản hoặc mật khẩu!");
                show(); // thử lại
                return;
            }
            System.out.println("✅ Đăng nhập thành công. Xin chào " + user.getFullName()
                    + " (" + user.getRole() + ")");
            router.route(user);
        }
    }
