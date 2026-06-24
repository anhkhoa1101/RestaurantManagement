/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.model;

/**
 *
 * @author khoa0
 */

import java.util.Date;

public class Invoice {
//Liệt kê 5 thuộc tính 
    private String invoiceId;
    private Order order;
    private Payment payment;
    private Double totalAmount;
    private Date issueDate;
    private InvoiceStatus status;

//  Constructor là một phương thức đặc biệt dùng để khởi tạo đối tượng, tên phải trùng với tên class và không có kiểu trả về.
public Invoice() {}

public Invoice(String invoiceId, Order order, Payment payment, Double totalAmount, Date issueDate, InvoiceStatus status) {
    this.invoiceId = invoiceId;
    this.order = order;
    this.payment = payment;
    this.totalAmount = totalAmount;
    this.issueDate = issueDate;
    this.status = InvoiceStatus.UNPAID; // Mặc định trạng thái là "UNPAID"
}

//hàm getter có nhiệm vụ lấy dữ liệu, setter gán dữ liệu

public String getInvoiceId()                    {return this.invoiceId;}
public void setInvoiceId(String invoiceId)      {this.invoiceId = invoiceId;}

public Order getOrder()                         {return this.order;}
public void setOrder(Order order)               {this.order = order;}

public Payment getPayment()                     {return this.payment;}
public void setPayment(Payment payment)         {this.payment = payment;}

public Double getTotalAmount()                  {return this.totalAmount;}
public void setTotalAmount(double totalAmount)  {this.totalAmount = totalAmount;}

public Date getIssueDate()                      {return this.issueDate;}
public void setIssueDate(Date issueDate)        {this.issueDate = issueDate;}

public InvoiceStatus getStatus()                       {return this.status;}
public void setStatus(InvoiceStatus status)            {this.status = status;}

//Phương thức là hành động mà class thực hiện được. Nhìn vào UML phần dấu + của Invoice.
//1. Khởi tạo đơn trên hệ thống
public void createInvoice() {
    this.issueDate = new Date();
    this.status = InvoiceStatus.UNPAID;
    this.totalAmount = order.getTotalPrice();
    System.out.println("Đã tạo hóa đơn: " + this.invoiceId); // ở đây dùng hàm this.invoiceId mục đích để lấy chính xác mã số hóa đơn đang x lý có thể làm 2 hướng: cái thứ 2 là System.out.prinln("Đã tạo hóa đơn thành công trên hệ thống!");
}

//2. In thông tin hóa đơn ra màn hình console
public void printInvoice() {
        System.out.println("\n========================================");
        System.out.println("          HÓA ĐƠN THANH TOÁN            ");
        System.out.println("========================================");
        System.out.println("Mã hóa đơn   : " + this.invoiceId);
        System.out.println("Mã đơn hàng  : " + this.order.getOrderId());
        System.out.println("Ngày xuất    : " + this.issueDate);
        System.out.println("Trạng thái   : " + this.status);
        System.out.println("----------------------------------------");
        System.out.println("TỔNG TIỀN    : " + this.totalAmount + " VND");
        if (payment != null) {
            System.out.println("Phương thức TT  : " + this.payment.getMethod());
            System.out.println("Mã GD : " + this.payment.getPaymentId());
        }
        System.out.println("========================================\n");
    }

//3. cập nhật trang thái từ bản In thông tin: Chờ thanh toán --> Đã thanh toán 
public void updateStatus(InvoiceStatus newStatus) {
    this.status = newStatus;
    System.out.println("Cập nhật trạng thái: " + newStatus);
}

//4. ở đây sẽ là Tìm kiếm hóa đơn bằng ID (nếu tìm thấy mã hóa đơn thì nó sẽ quay về return this còn ngược lại không tìm thấy sẽ return null)
public Invoice getInvoiceById(String id) {
    if (this.invoiceId.equals(id)) {
        return this;
      }
      return null;
    }

 @Override
    public String toString() {
        return "Invoice{" +
               "invoiceId='"   + invoiceId        + '\'' +
               ", orderId='" + order.getOrderId() + '\'' +
               ", totalAmount=" + totalAmount     +
               ", status='"    + status           + '\'' + '}';
    }
}


