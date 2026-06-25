package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Invoice;
import com.mycompany.restaurantmanagement.model.InvoiceStatus;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Payment;
import com.mycompany.restaurantmanagement.repository.InvoiceRepository;
import com.mycompany.restaurantmanagement.repository.OrderRepository; 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceService {

//THUỘC TÍNH
    private InvoiceRepository invoiceRepository;
    private OrderService orderService;
    private OrderRepository orderRepository;
    private TableService tableService;
    private InventoryService inventoryService;
//CONSTRUCTOR
public InvoiceService() {
        this.invoiceRepository = new InvoiceRepository();
        this.orderRepository   = new OrderRepository(); 
        this.tableService      = null; //chờ member 3
        this.inventoryService  = null; // chờ member 2
        this.orderService = new OrderService(orderRepository, tableService, inventoryService);
    }

    public InvoiceService(InvoiceRepository invoiceRepository,
                          OrderService orderService) {
        this.invoiceRepository = invoiceRepository;
        this.orderService      = orderService;
    }

//PHƯƠNG THỨC 

//1. Tạo hóa đơn từ Order
public Invoice createInvoiceFromOrder(Order order) {
    if (order == null) {
        System.out.println("Đơn hàng không hợp lệ!");
        return null;
    }

// tạo mã hóa đơn tự động
    String invoiceId = "INV-" + System.currentTimeMillis();

        // tạo Invoice từ Order
    Invoice invoice = new Invoice(
        invoiceId,
        order,
        null,                    // payment chưa có
        order.getTotalPrice(),   // lấy tổng tiền từ Order
        new Date(),              // ngày lập hóa đơn
        InvoiceStatus.UNPAID     // mặc định chưa thanh toán
    );

//Lưu vào reporitory
    invoiceRepository.save(invoice);
    System.out.println("Đã tạo hóa đơn: " + invoiceId);
    return invoice;
}

//2. In hóa đơn theo ID
public void printInvoice(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId);
        if (invoice != null) {
            invoice.printInvoice();
        } else {
            System.out.println("Không tìm thấy hóa đơn: " + invoiceId);
        }
    }

//3. Tìm hóa đơn theo ID
public Invoice getInvoiceById(String invoiceId) {
    if (invoiceId == null) {
        System.out.println("ID không hợp lệ!");
        return null;
    }
    Invoice invoice = invoiceRepository.findById(invoiceId);
    if (invoice == null) {
        System.out.println("Không tìm thấy hóa đơn: " + invoiceId);
    }
    return invoice;
}

// 4. Lấy danh sách hóa đơn theo khoảng thời gian 
public List<Invoice> getInvoiceByDateRange(Date from, Date to) {
    if (from == null || to == null) {
        System.out.println("Khoảng thời gian không hợp lệ!");
        return new ArrayList<>();
    }

    List<Invoice> result = new ArrayList<>();
    List<Invoice> allInvoices = invoiceRepository.findAll();
    for (Invoice invoice : allInvoices) {
            Date issueDate = invoice.getIssueDate();
            // kiểm tra issueDate nằm trong khoảng from → to
            if (issueDate != null
                && !issueDate.before(from)
                && !issueDate.after(to)) {
                result.add(invoice);
            }
        }

        System.out.println("Tìm thấy " + result.size() + " hóa đơn.");
        return result;
    }

// 5. Đánh dấu hóa đơn đã thanh toán 
public void markAsPaid(String invoiceId, Payment payment) {
    Invoice invoice = invoiceRepository.findById(invoiceId);
    if (invoice == null) {
        System.out.println("Không tìm thấy hóa đơn: " + invoiceId);
        return;
    }

// gán Payment vào Invoice
    invoice.setPayment(payment);
// cập nhật trạng thái -> PAID
    invoice.updateStatus(InvoiceStatus.PAID);
// lưu lại vào repository
    invoiceRepository.save(invoice);
    System.out.println("Đã thanh toán hóa đơn: " + invoiceId);
  }
}