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

        invoice.setInvoiceId(d[0]);

        invoice.setTotalAmount(Double.parseDouble(d[1]));

        invoice.setIssueDate(new Date(Long.parseLong(d[2])));

        invoice.setStatus(InvoiceStatus.valueOf(d[3]));

        if (d.length > 4 && !d[4].equals("null")) {

            invoice.setOrder(new Order(d[4], null));

        }

        return invoice;

    }

    @Override
    protected String toLine(Invoice invoice) {

        return
                invoice.getInvoiceId() +
                        "|" + invoice.getTotalAmount() +
                        "|" + invoice.getIssueDate().getTime() +
                        "|" + invoice.getStatus() + "|" +
                        (invoice.getOrder() == null ? "null" : invoice.getOrder().getOrderId());

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