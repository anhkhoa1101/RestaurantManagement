package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;
import com.mycompany.restaurantmanagement.model.Invoice;
import com.mycompany.restaurantmanagement.model.InvoiceStatus;
import com.mycompany.restaurantmanagement.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceRepository extends BaseRepository<Invoice, String> {

    public InvoiceRepository() {

        super(AppConfig.INVOICE_FILE_PATH);

    }

    @Override
    public Invoice findById(String id) {

        for (Invoice invoice : data) {

            if (

                    invoice.getInvoiceId().equals(id)

            ) {

                return invoice;

            }

        }

        return null;

    }

    @Override
    protected Invoice parseLine(String line) {

        String[] d = line.split("\\|");

        Invoice invoice = new Invoice();

        // 0. invoiceId
        invoice.setInvoiceId(d[0]);

        // 1. orderId
        Order order = new Order(d[1], null);
        invoice.setOrder(order);

        // 2. totalAmount
        invoice.setTotalAmount(Double.parseDouble(d[2]));

        // 3. issueDate (string → Date đơn giản hóa)
        // Nếu bạn không dùng timestamp thì nên parse kiểu này:
        try {
            invoice.setIssueDate(
                    new java.text.SimpleDateFormat("yyyy-MM-dd").parse(d[3])
            );
        } catch (Exception e) {
            invoice.setIssueDate(new Date());
        }

        // 4. status
        invoice.setStatus(InvoiceStatus.valueOf(d[4]));

        return invoice;
    }

    @Override
    protected String toLine(Invoice invoice) {

        return invoice.getInvoiceId()
                + "|" + (invoice.getOrder() != null ? invoice.getOrder().getOrderId() : "null")
                + "|" + invoice.getTotalAmount()
                + "|" + new java.text.SimpleDateFormat("yyyy-MM-dd").format(invoice.getIssueDate())
                + "|" + invoice.getStatus();
    }

    public Invoice findByOrderId(String orderId) {
        for (Invoice invoice : data) {
            if (
                    invoice.getOrder() != null
                            &&
                            invoice.getOrder().getOrderId().equals(orderId)

            ) {

                return invoice;

            }

        }

        return null;

    }

    public List<Invoice> findByDateRange(Date from, Date to) {

        List<Invoice> result = new ArrayList<>();

        for (Invoice invoice : data) {

            Date date = invoice.getIssueDate();

            if (

                    date != null

                            &&

                            !date.before(from)

                            &&

                            !date.after(to)

            ) {

                result.add(invoice);

            }

        }

        return result;

    }

}