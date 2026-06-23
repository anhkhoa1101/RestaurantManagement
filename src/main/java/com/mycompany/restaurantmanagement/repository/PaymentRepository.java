package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Payment;
import java.util.ArrayList;
import java.util.List;

// REPO NỐI.TXT
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class PaymentRepository {
    private static final String FILE_PATH = "data/payments.txt"; // đường dẫn đến file lưu trữ giao dịch.txt
    private List<Payment> paymentList;

    public PaymentRepository() {
        this.paymentList = new ArrayList<>();
        loadFromFile(); // Tải dữ liệu từ file khi khởi tạo repository.txt
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
                saveToFile(); // Ghi dữ liệu vào file sau khi cập nhật giao dịch.txt
                return;
            }
        }
        paymentList.add(payment);
        System.out.println("Đã thêm giao dịch: " + payment.getPaymentId());
        saveToFile(); // Ghi dữ liệu vào file sau khi thêm giao dịch.txt
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
                saveToFile(); // Ghi dữ liệu vào file sau khi xóa giao dịch.txt
                return true;
            }
        }
        System.out.println("Không tìm thấy giao dịch với ID: " + id);
        return false;
    }

// GHI DỮ LIỆU XUỐNG FILE.txt
    private void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Payment p : paymentList) {
                writer.write(
                    p.getPaymentId()   + "|" +
                    p.getInvoiceId()   + "|" +
                    p.getAmount()      + "|" +
                    p.getPaymentDate() + "|" +
                    p.getMethod()
                );
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

//Ghi dữ liệu từ file.txt
    private void loadFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    Payment p = new Payment(
                        parts[0],                       // paymentId
                        parts[1],                       // invoiceId
                        Double.parseDouble(parts[2]),   // amount
                        new Date(),                     // date hiện tại
                        parts[4]                        // method
                    );
                    paymentList.add(p);
                }

            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}

