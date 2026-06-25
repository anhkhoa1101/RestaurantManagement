/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.service;

/**
 *
 * @author khoa0
 */
import com.mycompany.restaurantmanagement.model.Payment;
import com.mycompany.restaurantmanagement.model.PaymentMethod;
import com.mycompany.restaurantmanagement.model.PaymentStatus;
import com.mycompany.restaurantmanagement.repository.PaymentRepository;
import java.util.Date;
import java.util.List;
   
// ===== THUỘC TÍNH =====
public class PaymentService {
    private PaymentRepository paymentRepository;
    private InvoiceService    invoiceService;

// ===== CONSTRUCTOR =====  
public PaymentService() {
    this.paymentRepository = new PaymentRepository();
    this.invoiceService = new InvoiceService();
}  

public PaymentService(PaymentRepository paymentRepository, InvoiceService invoiceService) {
    this.paymentRepository = paymentRepository;
    this.invoiceService = invoiceService;       
}

// ===== PHƯƠNG THỨC =====

//1. Tọa giao dịch thanh toán mới
public Payment createPayment(String invoiceId, double amount, PaymentMethod method) {

    // tạo mã giao dịch tự động 
    String paymentId = "PAY" + System.currentTimeMillis(); 

     // xử lý theo từng loại enum
        switch (method) {
            case CASH:
                System.out.println("Thanh toán tiền mặt: " + amount + " VND");
                break;
            case CARD:
                System.out.println("Thanh toán thẻ ngân hàng: " + amount + " VND");
                break;
            case BANK_TRANSFER:
                System.out.println("Chuyển khoản ngân hàng: " + amount + " VND");
                break;
            default:
                System.out.println("Phương thức không hợp lệ!");
        }

    // tạo đối tượng Payment mới
    Payment payment = new Payment(paymentId, invoiceId, amount, new Date(), method);    

    // lưu giao dịch vào repository
    payment.setStatus(PaymentStatus.PENDING);
    paymentRepository.save(payment);
    System.out.println("Đã tạo giao dịch thanh toán: " + paymentId);
    return payment;
}

//2. Xác nhận thanh toán
public boolean confirmPayment(String paymentId) {
    Payment payment = paymentRepository.findById(paymentId);
    if (payment == null) {
        System.out.println("Không tìm thấy giao dịch: " + paymentId);
        return false;
    }

    // xử lý thanh toán
    boolean success = payment.processPayment();
    if (success) {
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        System.out.println("Xác nhận thanh toán thành công: " + paymentId);
    } else {
        System.out.println("Xác nhận thanh toán thất bại: " + paymentId);
    }
    return success;
}
    
//3. Hủy thanh toán
public void cancelPayment(String paymentId) {
    Payment payment = paymentRepository.findById(paymentId);
    if (payment != null ) {
        payment.cancelPayment();
        payment.setStatus(PaymentStatus.CANCELLED);
        paymentRepository.save(payment);   // cập nhật lại trạng thái
        System.out.println("Hủy thanh toán thành công: " + paymentId);
    } else {
        System.out.println("Không tìm thấy giao dịch: " + paymentId);
    }
}

//4. Lấy lịch sử giao dịch thanh toán
public List<Payment> getPaymentHistory() {
    return paymentRepository.findAll();
   }
}