package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Payment;
import com.mycompany.restaurantmanagement.model.PaymentMethod;
import com.mycompany.restaurantmanagement.model.PaymentStatus;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentRepository {

//THUÔC TÍNH 
private List<Payment> payments;

//CONSTRUCTOR
public PaymentRepository() {
    this.payments = new ArrayList<>();
}

//Phương thức

//1.Thêm giao dịch mới
public void add(Payment payment) {
    if (payment == null) {
        System.out.println("Giao dịch không hợp lệ!");
        return;
    }
    payments.add(payment);
        System.out.println("Đã thêm giao dịch: " + payment.getPaymentId());
    }

//2. Tìm giao dịch theo ID
public Payment findById(String id) {
    if (id == null) {
        System.out.println("ID không hợp lệ!");
        return null;
    }
    for (Payment payment : payments) {
        if (payment.getPaymentId().equals(id)) {
            return payment;
        }
    }
    System.out.println("Không tìm thấy giao dịch: " + id);
    return null;
}

//3. lấy tất cả giao dịch
public List<Payment> findAll() {
    return payments;
}

//4. Tìm giao dịch theo mã hóa đơn
public Payment findByInvoiceId(String invoiceId) {
    if (invoiceId == null) {
        System.out.println("Mã hóa đơn không hợp lệ!");
        return null;
    }
    for (Payment payment : payments) {
        if (payment.getInvoiceId().equals(invoiceId)) {
            return payment;
        }
    }
    System.out.println("Không tìm thấy giao dịch với mã HĐ: " + invoiceId);
    return null;
}

//5. Cập nhật giao dịch 
public boolean update(Payment payment) {
    if (payment == null) {
        System.out.println("Giao dịch không hợp lệ!");
        return false;
    }
    for (int i = 0; i < payments.size(); i++) {
        if (payments.get(i).getPaymentId().equals(payment.getPaymentId())) {
            payments.set(i, payment);
            System.out.println("Đã cập nhật giao dịch: " + payment.getPaymentId());
            return true;
        }
    }
    System.out.println("Không tìm thấy giao dịch: " + payment.getPaymentId());
    return false;
    }

//6. Đọc dữ liệu từ file
public void loadFromFile(String path) {
    try {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        payments.clear(); // xóa danh sách cũ trước khi nạp
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                Payment payment = new Payment(    
                    parts[0],                            // paymentId      
                    parts[1],                            // invoiceId
                    Double.parseDouble(parts[2]),        // amount
                    new Date(),                          // paymentDate
                    PaymentMethod.valueOf(parts[4])    // method (enum) 
                ); 
                payment.setStatus(PaymentStatus.valueOf(parts[3]));  // status (enum)
                payments.add(payment);
            }
        }
        reader.close();
        System.out.println("Đã tải " + payments.size() + " giao dịch từ file. ");
    } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
    }
}

//7. Ghi dữ liệu xuống file
public void saveToFile(String path) {
    try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (Payment p : payments) {
                writer.write(
                    p.getPaymentId()      + "|" +
                    p.getInvoiceId()      + "|" +
                    p.getAmount()         + "|" +
                    p.getStatus()         + "|" +
                    p.getMethod()
                );
                writer.newLine();
            }
            writer.close();
            System.out.println("Đã lưu " + payments.size() + " giao dịch vào file.");
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

//TIỆN ÍCH  
// Thêm mới hoặc cập nhật

    public void save(Payment payment) {
        boolean updated = update(payment);
        if (!updated) {
            add(payment);
        }
    }
}
