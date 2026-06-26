package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Invoice;
import com.mycompany.restaurantmanagement.model.InvoiceStatus;
import com.mycompany.restaurantmanagement.model.Order;
import com.mycompany.restaurantmanagement.model.Payment;

import com.mycompany.restaurantmanagement.repository.InvoiceRepository;

import java.util.Date;
import java.util.List;

public class InvoiceService extends BaseService<Invoice, String> {

    private InvoiceRepository invoiceRepository;


    public InvoiceService(
            InvoiceRepository invoiceRepository
    ){

        super(invoiceRepository);

        this.invoiceRepository =
                invoiceRepository;

    }

    /*
     * Tạo hóa đơn từ Order
     */
    public Invoice createInvoiceFromOrder(Order order) {

        if (order == null) {

            throw new IllegalArgumentException("Order không hợp lệ");

        }

        String invoiceId = "INV" + System.currentTimeMillis();

        Invoice invoice = new Invoice(

                invoiceId,

                order,

                null,

                order.getTotalPrice(),

                new Date(),

                InvoiceStatus.UNPAID

        );

        add(invoice);

        return invoice;

    }

    /*
     * In hóa đơn
     */
    public void printInvoice(String invoiceId) {

        Invoice invoice = getById(invoiceId);

        if (invoice == null) {

            throw new IllegalArgumentException("Không tìm thấy hóa đơn");

        }

        invoice.printInvoice();

    }

    /*
     * Lấy hóa đơn
     */
    public Invoice getInvoice(String invoiceId) {

        return getById(invoiceId);

    }

    /*
     * Tìm theo ngày
     */
    public List<Invoice> getInvoiceByDateRange(Date from, Date to) {

        if (from == null || to == null) {

            throw new IllegalArgumentException("Khoảng ngày không hợp lệ");

        }

        return invoiceRepository.findByDateRange(from, to);

    }

    /*
     * Thanh toán
     */
    public void markAsPaid(String invoiceId, Payment payment) {

        Invoice invoice = getById(invoiceId);

        if (invoice == null) {

            throw new IllegalArgumentException("Không tìm thấy hóa đơn");

        }

        invoice.setPayment(payment);

        invoice.updateStatus(InvoiceStatus.PAID);

        update(invoice);

    }

    public Invoice getByOrderId(String orderId){
        return invoiceRepository
                .findByOrderId(orderId);
    }
}