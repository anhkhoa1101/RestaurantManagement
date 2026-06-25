package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Payment;
import com.mycompany.restaurantmanagement.model.PaymentMethod;
import com.mycompany.restaurantmanagement.model.PaymentStatus;

import com.mycompany.restaurantmanagement.repository.PaymentRepository;

import java.util.Date;
import java.util.List;

public class PaymentService extends BaseService<Payment, String> {

    private final PaymentRepository paymentRepository;

    private final InvoiceService invoiceService;

    public PaymentService(PaymentRepository paymentRepository, InvoiceService invoiceService) {

        super(paymentRepository);

        this.paymentRepository = paymentRepository;

        this.invoiceService = invoiceService;

    }

    public Payment createPayment(String invoiceId, double amount, PaymentMethod method) {

        if (invoiceId == null || invoiceId.trim().isEmpty()) {

            throw new IllegalArgumentException("InvoiceId không hợp lệ");

        }

        if (amount <= 0) {

            throw new IllegalArgumentException("Số tiền phải > 0");

        }

        String paymentId = "PAY" + System.currentTimeMillis();

        Payment payment = new Payment(

                paymentId,

                invoiceId,

                amount,

                new Date(),

                method

        );

        payment.setStatus(PaymentStatus.PENDING);

        add(payment);

        return payment;

    }

    public boolean confirmPayment(String paymentId) {

        Payment payment = getById(paymentId);

        if (payment == null) {

            throw new IllegalArgumentException("Không tìm thấy giao dịch");

        }

        boolean success = payment.processPayment();

        if (success) {

            payment.setStatus(PaymentStatus.SUCCESS);

            add(payment);

        }

        return success;

    }

    public void cancelPayment(String paymentId) {

        Payment payment = getById(paymentId);

        if (payment == null) {

            throw new IllegalArgumentException("Không tìm thấy giao dịch");

        }

        payment.cancelPayment();

        payment.setStatus(PaymentStatus.CANCELLED);

        add(payment);

    }

    public Payment getByInvoiceId(String invoiceId) {

        return paymentRepository.findByInvoiceId(invoiceId);

    }

    public List<Payment> getPaymentHistory() {

        return getAll();

    }

}