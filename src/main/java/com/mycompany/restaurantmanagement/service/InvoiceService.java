/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.service;

/**
 *
 * @author khoa0
 */

import com.mycompany.restaurantmanagement.model.Invoice;
import java.util.ArrayList;
import java.util.List;

//viết constructor
public class InvoiceService {
    private List<Invoice> invoiceList;

//tạo danh sách rỗng 
public InvoiceService() {
    this.invoiceList = new ArrayList<>(); 
}

//them hóa đơn vào danh sách 
public void addInvoice(Invoice invoice) {
    if (invoice == null) { // không có giá trị --> null
        System.out.println("Hóa đơn không hợp lệ!");
        return;
    }
    invoiceList.add(invoice); // thêm giá trị và lấy mã hóa đơn và in ra thông báo 
    System.out.println("Đã thêm hóa đơn: " + invoice.getInvoiceId());
}

// Lưu hóa đơn sau khi đã chỉnh sửa
// TH1:lưu null
public void saveInvoice(Invoice invoice) {
    if (invoice == null) {
        System.out.println("Hóa đơn không hợp lệ!");
        return;
    }
//TH2:lưu null đã có 
    for (int i = 0; i < invoiceList.size(); i++) { //duyệt từng hóa đơn trong danh sách bắt đầu số 0 và tăng dần đến hết danh sách 
        if (invoiceList.get(i).getInvoiceId().equals(invoice.getInvoiceId())) { //Lấy danh sách từ vị trí số 0, lấy mã hóa đơn và so sánh các chuỗi hóa đơn (ID) nếu trùng thì cập nhật mới và lưu hóa đơn mình muốn lưu 

            invoiceList.set(i, invoice); //thay thế hóa đơn cũ bằng hóa đơn mới và in ra báo lưu và back về 
            System.out.println("Đã lưu hóa đơn: " + invoice.getInvoiceId());
            return;
        }
    }
//Th3:thêm mới
    addInvoice(invoice); // nếu đã duyệt hết danh sách nhưng không tìm thấy ID trùng thì add mã đơn mới vào danh sách 
}

//Tìm hóa đơn theo ID
public Invoice findById(String id) {
    if (id == null) {
        System.out.println("ID không hợp lệ!");
        return null;
    }

    for (int i = 0; i < invoiceList.size(); i++) {
        if (invoiceList.get(i).getInvoiceId().equals(id)) { //so sánh id
            return invoiceList.get(i);
        }
    }
    return null;
}

public List<Invoice> getAllInvoices() {
    return invoiceList;
   }

//Tìm hóa đơn theo ID và cập nhật trạng thái 
public void updateStatus(String id, String newStatus) {
    Invoice invoice = findById(id);
    if (invoice != null) {
        invoice.updateStatus(newStatus);
    } else {System.out.println("Không tìm thấy hóa đơn: " + id);}
  }
}
