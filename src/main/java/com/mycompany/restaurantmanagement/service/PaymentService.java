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
import java.util.ArrayList;
import java.util.List;
   
// INTERFACE ĐA HÌNH CHO CÁC PHƯƠNG THỨC THANH TOÁN
public class PaymentService {
    public interface PaymentMethod {
        boolean pay(double amount);
        String getMethodName();
    }

    //TIỀN MẶT
    public class CashPayment implements PaymentMethod {
        @Override
        public boolean pay(double amount) {
            System.out.println("Thanh toán bằng tiền mặt: " + amount + " VND");
            return true;
        }
        @Override
        public String getMethodName() {
            return "Tiền mặt";
        }
    }
    
    //QUÉT QR
    public class QRPayment implements PaymentMethod {
        @Override
        public boolean pay(double amount) {
            System.out.println("Thanh toán bằng quét QR: " + amount + " VND");
            return true;
        }
        @Override
        public String getMethodName() {
            return "Quét QR";
        }
    }

    private List<Payment> paymentList;

public PaymentService() {
    this.paymentList = new ArrayList<>();
}
//Xử lý thanh toán với phương thức truyền vào

public boolean processPayment(String paymentId, PaymentMethod method) {
    Payment payment = findById(paymentId);
    if (payment != null) {
        System.out.println("Phương thức thanh toán: " + method.getMethodName());
        return method.pay(payment.getAmount());
    }
    System.out.println("Không tìm thấy giao dịch: " + paymentId);
    return false;
}

// THÊM GIAO DỊCH
public void addPayment(Payment payment) {
    if (payment == null) {
        System.out.println("Giao dịch không hợp lệ!");
        return;
    }
    paymentList.add(payment);
    System.out.println("Đã thêm giao dịch: " + payment.getPaymentId());
}

// TÌM GIAO DỊCH THEO ID
public Payment findById(String id) {
    if (id == null) {
        System.out.println("ID không hợp lệ!");
        return null;
    }
    for (int i = 0; i < paymentList.size(); i++) {
        if (paymentList.get(i).getPaymentId().equals(id)) {
            return paymentList.get(i);
        }
    }
    return null;
}

// LẤY DANH SÁCH GIAO DỊCH
public List<Payment> getAllPayments() {
    return paymentList;
}
// HỦY GIAO DỊCH
public boolean cancelPayment(String paymentId) {
    Payment payment = findById(paymentId);
    if (payment != null) {
        payment.cancelPayment();  
        return true;   
    } else {
        System.out.println("Không tìm thấy giao dịch: " + paymentId);
        return false;
    } 
  }
}