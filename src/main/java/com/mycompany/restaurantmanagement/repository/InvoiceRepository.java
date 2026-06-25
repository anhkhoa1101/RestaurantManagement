package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Invoice;
import com.mycompany.restaurantmanagement.model.InvoiceStatus;
import com.mycompany.restaurantmanagement.config.AppConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceRepository {
    private List<Invoice> invoices;

//CONSTRUCTOR
public InvoiceRepository() {
    this.invoices = new ArrayList<>();
}

//Phương thức

// 1. Thêm hóa đơn mới
public void add(Invoice invoice) {
    if (invoice == null) {
        System.out.println("Hóa đơn không hợp lệ!");
        return;
    }
    invoices.add(invoice);
    saveToFile(AppConfig.INVOICE_FILE_PATH);
}

// 2. Tìm hóa đơn theo ID
public Invoice findById(String id) {
    if (id == null) {
        System.out.println("ID không hợp lệ!");
        return null;
    }
    for (Invoice invoice : invoices) {
        if (invoice.getInvoiceId().equals(id)) {
            return invoice;
        }
    }
    System.out.println("Không tìm thấy hóa đơn: " + id);
    return null;
}

// 3.Lấy tất cả hóa đơn
public List<Invoice> findAll() {
    return invoices;
}

// 4. Tìm hóa đơn theo mã đơn hàng
public Invoice findByOrderId(String orderId) {
    if (orderId == null) {
        System.out.println("Mã đơn hàng không hợp lệ!");
        return null;
    }
    for (Invoice invoice : invoices) {
        if (invoice.getOrder().getOrderId().equals(orderId)) {
            return invoice;
        }
    }
    System.out.println("Không tìm thấy hóa đơn với mã đơn hàng: " + orderId);
    return null;
}

// 5. Tìm hóa đơn theo khoảng thời gian 
public List<Invoice> findByDateRange(Date from, Date to) {
    if (from == null || to == null) {
       System.out.println("Khoảng thời gian không hợp lệ!");
       return new ArrayList<>();
    }
    List<Invoice> result = new ArrayList<>();
        for (Invoice invoice : invoices) {
            Date issueDate = invoice.getIssueDate();
            if (issueDate != null
                && !issueDate.before(from)
                && !issueDate.after(to)) {
                result.add(invoice);
            }
        }
        System.out.println("Tìm thấy " + result.size() + " hóa đơn.");
        return result;
}

// 6.Cập nhật hóa đơn
public boolean update(Invoice invoice) {
    if (invoice == null) {
        System.out.println("Hóa đơn không hợp lệ!");
        return false;
    }
    for (int idx = 0; idx < invoices.size(); idx++) {
        if (invoices.get(idx).getInvoiceId().equals(invoice.getInvoiceId())) {
        invoices.set(idx, invoice);
                System.out.println("Đã cập nhật hóa đơn: " + invoice.getInvoiceId());
                return true;
            }
        }
        System.out.println("Không tìm thấy hóa đơn: " + invoice.getInvoiceId());
        return false;
    }

// 7. Đọc dữ liệu từ file
public void loadFromFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            invoices.clear(); // xóa danh sách cũ trước khi nạp
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    Invoice invoice = new Invoice();
                    invoice.setInvoiceId(parts[0]);           // invoiceId
                    invoice.setTotalAmount(Double.parseDouble(parts[1])); // totalAmount
                    invoice.setIssueDate(new Date());          // date
                    invoice.setStatus(InvoiceStatus.valueOf(parts[3])); // status
                    invoices.add(invoice);
                }
            }
            reader.close();
            System.out.println("Đã tải " + invoices.size() + " hóa đơn từ file.");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
    }

// 8.Ghi dữ liệu xuống file
public void saveToFile(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (Invoice invoice : invoices) {
                writer.write(
                    invoice.getInvoiceId()               + "|" +
                    invoice.getTotalAmount()             + "|" +
                    invoice.getIssueDate()               + "|" +
                    invoice.getStatus()
                );
                writer.newLine();
            }
            writer.close();
            System.out.println("Đã lưu " + invoices.size() + " hóa đơn vào file.");
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

//TIỆN ÍCH
// Thêm mới hoặc cập nhậ
    public void save(Invoice invoice) {
        boolean updated = update(invoice);
        if (!updated) {
           add(invoice);
        }
    }
}

