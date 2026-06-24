package com.mycompany.restaurantmanagement.ui;

import com.mycompany.restaurantmanagement.model.Invoice;
import com.mycompany.restaurantmanagement.model.Payment;
import com.mycompany.restaurantmanagement.model.PaymentMethod;
import com.mycompany.restaurantmanagement.service.InvoiceService;
import com.mycompany.restaurantmanagement.service.PaymentService;
import java.util.Scanner;

//THUỘC TÍNH
public class PaymentUI {
    private InvoiceService invoiceService;
    private PaymentService paymentService;
    private Scanner scanner;

    // CONSTRUCTOR
    public PaymentUI() {
        this.invoiceService = new InvoiceService();
        this.paymentService = new PaymentService();
        this.scanner = new Scanner(System.in);
    }

    // PHƯƠNG THỨC
    // 1. Xử lý thanh toán
    public void processCheckout() {
        System.out.println("\n===== THANH TOÁN =====");

        System.out.print("Nhập mã đơn hàng: ");
        String orderId = scanner.nextLine();

        Invoice invoice = invoiceService.getInvoiceById(orderId);
        if (invoice == null) {
            System.out.println("Không tìm thấy đơn hàng: " + orderId);
            return;
        }

        invoice.printInvoice();

        System.out.println("\nChọn phương thức thanh toán:");
        System.out.println("1. Tiền mặt (CASH)");
        System.out.println("2. Thẻ ngân hàng (CARD)");
        System.out.println("3. Chuyển khoản (BANK_TRANSFER)");
        System.out.print("Lựa chọn: ");
        int methodChoice = scanner.nextInt();
        scanner.nextLine();

        PaymentMethod method;
        switch (methodChoice) {
            case 1:
                method = PaymentMethod.CARD;
                break;
            case 2:
                method = PaymentMethod.CARD;
                break;
            case 3:
                method = PaymentMethod.BANK_TRANSFER;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }

        Payment payment = paymentService.createPayment(
                invoice.getInvoiceId(),
                invoice.getTotalAmount(),
                method);

        boolean success = paymentService.confirmPayment(payment.getPaymentId());
        if (success) {
            invoiceService.markAsPaid(invoice.getInvoiceId(), payment);
            System.out.println("Thanh toán thành công!");
        } else {
            System.out.println("Thanh toán thất bại!");
        }
    }

    // 2. Xem hóa đơn
    public void viewInvoice() {
        System.out.println("\n===== XEM HÓA ĐƠN =====");
        System.out.print("Nhập mã hóa đơn: ");
        String invoiceId = scanner.nextLine();
        invoiceService.printInvoice(invoiceId);
    }

    // 3. Menu chính
    public void run() {
        int choice;
        do {
            System.out.println("\n===== QUẢN LÝ THANH TOÁN =====");
            System.out.println("1. Thanh toán đơn hàng");
            System.out.println("2. Xem hóa đơn");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    processCheckout();
                    break;
                case 2:
                    viewInvoice();
                    break;
                case 0:
                    System.out.println("Thoát module thanh toán.");
                    break;
            }
        } while (choice != 0);
    }
}