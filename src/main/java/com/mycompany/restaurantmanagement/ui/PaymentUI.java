/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.ui;

/**
 *
 * @author khoa0
 */
import com.mycompany.restaurantmanagement.model.Payment;
import com.mycompany.restaurantmanagement.service.PaymentService;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class PaymentUI {
    private Scanner scanner; /// đọc dữu liệu người dùng 
    private PaymentService paymentService; /// gọi các hàm addPayment, processPayment, cancelPayment   

//Constructor
public PaymentUI() {
    scanner = new Scanner(System.in); //tạo đối tượng Scanner để đọc dữ liệu từ người dùng
    paymentService = new PaymentService(); //tạo đối tượng PaymentService để gọi các hàm addPayment, processPayment, cancelPayment
}

public void showMenu() {
    int choice;
    do {
        System.out.println("=== Quản lý thanh toán ===");
        System.out.println("1. Thêm giao dịch");
        System.out.println("2. Xử lý thanh toán");
        System.out.println("3. Hủy giao dịch");
        System.out.println("4. Hiển thị tất cả giao dịch");
        System.out.println("0. Thoát");
        System.out.print("Chọn một tùy chọn: ");
        choice = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới sau khi đọc số nguyên

        switch (choice) {
            case 1:
                addPayment();
                break;
            case 2:
                processPayment();
                break;
            case 3:
                cancelPayment();
                break;
            case 4:
                displayAllPayments();
                break;
            case 0:
                System.out.println("Thoát chương trình.");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    } while (choice != 0);
}

//1. Thêm giao dịch
public void addPayment() {
    System.out.print("Nhập mã giao dịch: ");
    String paymentId = scanner.nextLine(); //scanner đọc dữ liệu từ người dùng và lưu vào biến paymentId

    System.out.print("Nhập mã hóa đơn: ");
    String invoiceId = scanner.nextLine(); //scanner đọc dữ liệu từ người dùng và lưu

    System.out.print("Nhập số tiền: ");
    double amount = scanner.nextDouble(); //quét và đọc số thực từ người dùng và lưu vào biến amount

    scanner.nextLine(); // Đọc bỏ dòng mới sau khi đọc số thực

    System.out.print("Nhập phương thức thanh toán: ");
    String method = scanner.nextLine(); //scanner hiện thị thông báo "Nhập phương thức thanh toán: " và đọc dữ liệu từ người dùng, lưu vào biến method

    Date date = new Date(); // Lấy ngày hiện tại
    Payment payment = new Payment(paymentId, invoiceId, amount, date, method);

    paymentService.addPayment(payment); //lưu giao dịch vào ArrayList
    System.out.println("Giao dịch đã được thêm thành công!");
}

//2. Xử lý thanh toán
public void processPayment() {
    System.out.print("Nhập mã giao dịch cần xử lý: ");
    String paymentId = scanner.nextLine(); //scanner đọc dữ liệu từ người dùng và lưu vào biến paymentId

    boolean success = paymentService.processPayment(paymentId); //gọi hàm processPayment trong PaymentService để xử lý giao dịch và paymentService sẽ trả về true nếu giao dịch được xử lý thành công, ngược lại trả về false
    if (success) {
        System.out.println("Xử lý giao dịch thành công!");
    } else {
        System.out.println("Xử lý giao dịch thất bại!");
    }
}

//3. Hủy giao dịch
public void cancelPayment() {
    System.out.println("Nhập mã giao dịch cần hủy: ");
    String paymentId = scanner.nextLine();

    boolean success = paymentService.cancelPayment(paymentId); //gọi hàm cancelPayment trong PaymentService để hủy giao dịch và paymentService sẽ trả về true nếu giao dịch được hủy thành công, ngược lại trả về false
    if (success) {
        System.out.println("Hủy giao dịch thành công!");
    } else {
        System.out.println("Hủy giao dịch thất bại!");
    }
}

//4. Hiển thị tất cả giao dịch
public void displayAllPayments() {
    System.out.println("=== Danh sách giao dịch ===");
    List<Payment> list = paymentService.getAllPayments(); //gọi hàm getAllPayments trong PaymentService để lấy danh sách tất cả giao dịch và lưu vào biến list
    
    for (Payment payment : list) { //sử dụng vòng lặp for-each để duyệt qua từng phần tử trong danh sách list và in thông tin của từng giao dịch ra
        System.out.println("Mã giao dịch: " + payment.getPaymentId());
        System.out.println("Số tiền: " + payment.getAmount());
        System.out.println("Phương thức thanh toán: " + payment.getMethod());
        System.out.println("Ngày: " + payment.getDate());
        System.out.println("-------------------------");
    }
  }

}