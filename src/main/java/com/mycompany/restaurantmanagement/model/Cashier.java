package com.mycompany.restaurantmanagement.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * [Member 1] Thu ngân — thanh toán, in hóa đơn, xem doanh thu ca.
 *
 * Lưu ý: shiftStart/shiftEnd trong UML là Date — ở đây dùng LocalDateTime
 * để có cả ngày + giờ ca làm việc, vẫn giữ đúng tên phương thức theo UML.
 */
public class Cashier extends User {

    public static final DateTimeFormatter SHIFT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int cashierId;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;

    // Tổng doanh thu xử lý trong ca hiện tại (cộng dồn mỗi lần processCheckout thành công).
    private double dailyRevenue;

    public Cashier(int userId, String username, String password, String fullName,
                   String email, boolean isActive, int cashierId,
                   LocalDateTime shiftStart, LocalDateTime shiftEnd) {
        super(userId, username, password, fullName, email, isActive);
        this.cashierId = cashierId;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.dailyRevenue = 0.0;
    }

    public int getCashierId() {
        return cashierId;
    }

    public LocalDateTime getShiftStart() {
        return shiftStart;
    }

    public LocalDateTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftStart(LocalDateTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public void setShiftEnd(LocalDateTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    /**
     * UML method: processCheckout(order: Order) : Invoice
     * Module 1 chỉ định nghĩa "điểm kết nối" sang Module 4. Việc tạo Invoice thật
     * (tính tổng tiền, lưu Invoice) thuộc trách nhiệm Module 4 (InvoiceService).
     * Ở đây dùng Object thay vì import trực tiếp class Order/Invoice của module khác,
     * để Module 1 không phụ thuộc cứng vào code chưa hoàn thiện của Module 3 & 4.
     *
     * Khi tích hợp toàn hệ thống, chữ ký thật sẽ là:
     *     public Invoice processCheckout(Order order)
     * và phần thân sẽ gọi InvoiceService.createInvoice(order) rồi cộng dailyRevenue.
     */
    public Object processCheckout(Object order) {
        System.out.println("[Cashier] " + fullName + " đang xử lý thanh toán (gọi sang Module 4 - InvoiceService.createInvoice).");
        return null; // placeholder — thay bằng Invoice thật khi tích hợp module 4
    }

    public void recordRevenue(double amount) {
        this.dailyRevenue += amount;
    }

    public double viewDailyRevenue() {
        return dailyRevenue;
    }

    @Override
    public String getRole() {
        return "CASHIER";
    }

    /**
     * Format dòng lưu file (phân tách bằng '|'):
     * CASHIER|userId|username|password|fullName|email|isActive|cashierId|shiftStart|shiftEnd|dailyRevenue
     */
    @Override
    public String toFileLine() {
        return String.join("|",
                getRole(),
                String.valueOf(userId),
                username,
                password,
                fullName,
                email,
                String.valueOf(isActive),
                String.valueOf(cashierId),
                shiftStart.format(SHIFT_FORMAT),
                shiftEnd.format(SHIFT_FORMAT),
                String.valueOf(dailyRevenue)
        );
    }
}