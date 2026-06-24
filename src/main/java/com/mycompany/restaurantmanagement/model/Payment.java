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
    private PaymentMethod method;       // "Tiền mặt" hoặc "Quét QR" --> // PaymentMethod enum
    private PaymentStatus status;       // "PENDING" | "SUCCESS" | "CANCELLED" --> PaymentStatus enum

//Liệt kê các constructor
public Payment() {}

public Payment(String paymentId, String invoiceId, Double amount, Date paymentDate, PaymentMethod method) {
    this.paymentId = paymentId;
    this.invoiceId = invoiceId;
    this.amount = amount;
    this.paymentDate = paymentDate;
    this.method = method;
    this.status = PaymentStatus.PENDING; // Mặc định trạng thái là "PENDING"
}

public String getPaymentId()                    {return this.paymentId;}
public void setPaymentId(String paymentId)      {this.paymentId = paymentId;}    

public String getInvoiceId()                    {return this.invoiceId; }
public void setInvoiceId(String invoiceId)      {this.invoiceId = invoiceId;}

public Double getAmount()                       {return this.amount;}
public void setAmount(Double amount)            {this.amount = amount;}

public Date getPaymentDate()                    {return this.paymentDate;}
public void setPaymentDate(Date paymentDate)    {this.paymentDate = paymentDate;}

public PaymentMethod getMethod()                       {return this.method;}
public void setMethod(PaymentMethod method)            {this.method = method;}

public PaymentStatus getStatus()                       {return this.status;}
public void setStatus(PaymentStatus status)            {this.status = status;}

//PHƯƠNG THỨC

//1. Xử lí thanh toán 
public boolean processPayment() {
    if (this.amount > 0) {
        this.status = PaymentStatus.SUCCESS;
        System.out.println("Thanh toán thành công: " + this.amount + " VND");
        return true;
    }
    this.status = PaymentStatus.CANCELLED;
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
        System.out.println("Trạng thái : " + status);
    }

//3. Lấy trạng thái thanh toán
public PaymentStatus getPaymentStatus() {
    return this.status;
}

//4. Hủy thanh toán
public void cancelPayment() {
    this.status = PaymentStatus.CANCELLED;
    this.amount = 0.0;
    System.out.println("Đã hủy thanh toán: " + this.paymentId);
}

 @Override
    public String toString() {
        return "Payment{" +
               "paymentId='"   + paymentId  + '\'' +
               ", invoiceId='" + invoiceId + '\'' +
               ", amount="     + amount     +
               ", method='"    + method     + '\'' +
               ", status='"    + status     + '\'' + '}';
    }
}