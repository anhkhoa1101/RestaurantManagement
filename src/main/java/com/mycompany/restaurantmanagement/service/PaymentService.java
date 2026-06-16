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

public class PaymentService {
    private List<Payment> paymentList;

public PaymentService() {
    this.paymentList = new ArrayList<>();
}

public void addPayment(Payment payment) {
    if (payment == null) {
        System.out.println("Giao dịch không hợp lệ!");
        return;
    }
    paymentList.add(payment);
    System.out.println("Đã thêm giao dịch: " + payment.getPaymentId());
}
public boolean processPayment(String paymentId) {
    if (paymentId == null) {
        System.out.println("Mã giao dịch không hợp lệ!");
        return false;
    }
    Payment payment = findById(paymentId);
    if (payment != null) {
        return payment.processPayment();
    } else {
        System.out.println("Không tìm thấy giao dịch: " + paymentId);
        return false;
    }
}
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
public List<Payment> getAllPayments() {
    return paymentList;
}

public void cancelPayment(String paymentId) {
    Payment payment = findById(paymentId);
    if (payment != null) {
        payment.cancelPayment();    
    } else {System.out.println("Không tìm thấy giao dịch: " + paymentId);}
  }
}