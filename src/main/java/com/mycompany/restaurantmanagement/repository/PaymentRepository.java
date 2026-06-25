package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.config.AppConfig;

import com.mycompany.restaurantmanagement.model.Payment;
import com.mycompany.restaurantmanagement.model.PaymentMethod;
import com.mycompany.restaurantmanagement.model.PaymentStatus;

import java.util.Date;

public class PaymentRepository extends BaseRepository<Payment, String> {

    public PaymentRepository() {

        super(AppConfig.PAYMENT_FILE_PATH);

    }

    @Override
    public Payment findById(String id) {

        if (id == null) {

            return null;

        }

        for (Payment payment : data) {

            if (

                    payment.getPaymentId().equals(id)

            ) {

                return payment;

            }

        }

        return null;

    }

    @Override
    protected Payment parseLine(String line) {

        try {

            String[] d = line.split("\\|");

            if (d.length < 6) {

                return null;

            }

            Payment payment = new Payment(

                    d[0],

                    d[1],

                    Double.parseDouble(d[2]),

                    new Date(Long.parseLong(d[3])),

                    PaymentMethod.valueOf(d[5])

            );

            payment.setStatus(

                    PaymentStatus.valueOf(d[4])

            );

            return payment;

        } catch (Exception e) {

            return null;

        }

    }

    @Override
    protected String toLine(Payment payment) {

        return

                payment.getPaymentId()

                        + "|"

                        + payment.getInvoiceId()

                        + "|"

                        + payment.getAmount()

                        + "|"

                        + payment.getPaymentDate().getTime()

                        + "|"

                        + payment.getStatus()

                        + "|"

                        + payment.getMethod();

    }

    public Payment findByInvoiceId(String invoiceId) {

        if (invoiceId == null) {

            return null;

        }

        for (

                Payment payment : data

        ) {

            if (

                    invoiceId.equals(

                            payment.getInvoiceId()

                    )

            ) {

                return payment;

            }

        }

        return null;

    }

}