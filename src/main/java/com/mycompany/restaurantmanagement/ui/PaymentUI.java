package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Invoice;
import com.mycompany.restaurantmanagement.model.Payment;
import com.mycompany.restaurantmanagement.model.PaymentMethod;

import com.mycompany.restaurantmanagement.service.InvoiceService;
import com.mycompany.restaurantmanagement.service.PaymentService;

import java.util.Scanner;

public class PaymentUI {

    private InvoiceService invoiceService;

    private PaymentService paymentService;

    private Scanner scanner;

    public PaymentUI(
            InvoiceService invoiceService,
            PaymentService paymentService
    ) {

        this.invoiceService =
                invoiceService;

        this.paymentService =
                paymentService;

        this.scanner =
                new Scanner(System.in);

    }

    /*
     * Thanh toán hóa đơn
     */
    public void processCheckout() {

        try {

            System.out.println(
                    "\n===== THANH TOÁN ====="
            );

            System.out.print(
                    "Nhập mã hóa đơn: "
            );

            String invoiceId =
                    scanner.nextLine();

            Invoice invoice =
                    invoiceService.getById(
                            invoiceId
                    );

            if (
                    invoice == null
            ) {

                System.out.println(
                        "Không tìm thấy hóa đơn"
                );

                return;

            }

            invoice.printInvoice();

            PaymentMethod method =
                    choosePaymentMethod();

            if (
                    method == null
            ) {

                return;

            }

            Payment payment =
                    paymentService.createPayment(

                            invoice.getInvoiceId(),

                            invoice.getTotalAmount(),

                            method

                    );

            boolean success =
                    paymentService
                            .confirmPayment(
                                    payment.getPaymentId()
                            );

            if (
                    success
            ) {

                invoiceService.markAsPaid(

                        invoice.getInvoiceId(),

                        payment

                );

                System.out.println(
                        "\nThanh toán thành công."
                );

            } else {

                System.out.println(
                        "\nThanh toán thất bại."
                );

            }

        } catch (Exception e) {

            System.out.println(
                    e.getMessage()
            );

        }

    }

    /*
     * Chọn phương thức
     */
    private PaymentMethod choosePaymentMethod() {

        System.out.println(
                "\n===== CHỌN PHƯƠNG THỨC ====="
        );

        System.out.println(
                "1. CASH"
        );

        System.out.println(
                "2. CARD"
        );

        System.out.println(
                "3. BANK_TRANSFER"
        );

        System.out.print(
                "Lựa chọn: "
        );

        int choice =
                Integer.parseInt(
                        scanner.nextLine()
                );

        switch (choice) {

            case 1:
                return PaymentMethod.CASH;

            case 2:
                return PaymentMethod.CARD;

            case 3:
                return PaymentMethod.BANK_TRANSFER;

            default:

                System.out.println(
                        "Lựa chọn không hợp lệ"
                );

                return null;

        }

    }

    /*
     * Xem hóa đơn
     */
    public void viewInvoice() {

        try {

            System.out.print(
                    "Nhập mã hóa đơn: "
            );

            String id =
                    scanner.nextLine();

            invoiceService
                    .printInvoice(
                            id
                    );

        }

        catch (Exception e) {

            System.out.println(
                    e.getMessage()
            );

        }

    }

    /*
     * Menu
     */
    public void run() {

        while (true) {

            System.out.println(
                    "\n===== PAYMENT MENU ====="
            );

            System.out.println(
                    "1. Thanh toán"
            );

            System.out.println(
                    "2. Xem hóa đơn"
            );

            System.out.println(
                    "0. Thoát"
            );

            System.out.print(
                    "Chọn: "
            );

            int choice =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

            switch (choice) {

                case 1:

                    processCheckout();

                    break;

                case 2:

                    viewInvoice();

                    break;

                case 0:

                    return;

                default:

                    System.out.println(
                            "Không hợp lệ"
                    );

            }

        }

    }

}