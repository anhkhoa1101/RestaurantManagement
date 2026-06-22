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

public class Payment {
    //Liệt kê 5 thuộc tính payment
    private String paymentId;
    private String invoiceId;
    private Double amount;
    private Date paymentDate;
    private String method;

//Liệt kê các constructor
public Payment() {}
public Payment(String paymentId, String invoiceId, Double amount, Date paymentDate, String method) {
    this.paymentId = paymentId;
    this.invoiceId = invoiceId;
    this.amount = amount;
    this.paymentDate = paymentDate;
    this.method = method;
}

public String getPaymentId() {return this.paymentId;}
public void setPaymentId(String paymentId) {this.paymentId = paymentId;}    

public String getInvoiceId() {return this.invoiceId; }
public void setInvoiceId(String invoiceId) {this.invoiceId = invoiceId;}

public Double getAmount() {return this.amount;}
public void setAmount(Double amount) { this.amount = amount;}

public Date getPaymentDate() {return this.paymentDate;}
public void setPaymentDate(Date paymentDate) {this.paymentDate = paymentDate;}

public Date getDate() {
    return this.paymentDate;
}

public String getMethod() {return this.method;}
public void setMethod(String method) {this.method = method;}

//1. Sử dụng hàm boolean mục đích trả về true and false và nếu số tiền > 0 thì in ra thông báo và sẽ trả về true và đồng thời in ra dòng thông báo ("Thanh toán thành công!")ngược lại < 0 thì trả về false
public boolean processPayment() {
    if (this.amount > 0) {
        System.out.println("Thanh toán thành công: "+ this.amount + " VND");
        return true;
    }
    return false;
}

//2. In thông tin giao dịch
public void recordTransaction() {
    System.out.println("===== GIAO DỊCH =====");
    System.out.println("Mã GD      : " + paymentId);
    System.out.println("Mã HD      : " + invoiceId);
    System.out.println("Số tiền    : " + amount);
    System.out.println("Ngày TT    : " + paymentDate);
    System.out.println("Phương thức: " + method);
}

//3. Cập nhật trạng thái từ bản thông tin giao dịch: "Đã thanh toán" or "Chưa thanh toán"
public String getPaymentStatus() {
    if (this.amount > 0) {
        return "Đã thanh toán";
    }
        return "Chưa thanh toán";
}

//4. Hủy thanh toán 
public void cancelPayment() {
    this.amount = 0.0;
    System.out.println("Đã hủy thanh toán: " + this.paymentId);
}

 @Override
    public String toString() {
        return "Payment{" +
               "paymentId='"  + paymentId  + '\'' +
               ", invoiceId='" + invoiceId + '\'' +
               ", amount="    + amount     +
               ", method='"   + method     + '\'' + '}';
    }
}