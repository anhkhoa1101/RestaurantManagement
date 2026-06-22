package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private List<Payment> paymentList;

    public PaymentRepository() {
        this.paymentList = new ArrayList<>();
    }

// LƯU GIAO DỊCH và CẬP NHẬT GIAO DỊCH
    public void save(Payment payment) {
        if (payment == null) {
            System.out.println("Dữ liệu không hợp lệ!");
            return;
        }
        for (int i = 0; i < paymentList.size(); i++) {
            if (paymentList.get(i).getPaymentId().equals(payment.getPaymentId())) { //Duyệt từng phần tử trong danh sách và so sánh ID của từng phần tử với ID cần lưu
                paymentList.set(i, payment); // Cập nhật giao dịch nếu đã tồn tại
                System.out.println("Đã cập nhật giao dịch: " + payment.getPaymentId());
                return;
            }
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
        System.out.println("Không tìm thấy giao dịch với ID: " + id);
        return null;
    }
// LẤY DANH SÁCH GIAO DỊCH
    public List<Payment> findAll() {
        return paymentList;
    }

// XÓA GIAO DỊCH THEO ID
    public boolean delete(String id ) {
        if (id == null) {
            System.out.println("ID không hợp lệ!");
            return false;
        }
        for (int i = 0; i < paymentList.size(); i++) {
            if (paymentList.get(i).getPaymentId().equals(id)) {
                paymentList.remove(i);
                System.out.println("Đã xóa giao dịch với ID: " + id);
                return true;
            }
        }
        System.out.println("Không tìm thấy giao dịch với ID: " + id);
        return false;
    }
}

