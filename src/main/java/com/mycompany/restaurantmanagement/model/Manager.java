package com.mycompany.restaurantmanagement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * [Member 1] Quản lý — có toàn quyền: quản lý nhân viên, xem báo cáo, cài đặt hệ thống.
 */
public class Manager extends User {

    private int managerId;
    private String department;

    // Danh sách nhân viên do Manager này quản lý (giữ tham chiếu trong bộ nhớ).
    private List<Employee> managedEmployees = new ArrayList<>();

    public Manager(int userId, String username, String password, String fullName,
                   String email, boolean isActive, int managerId, String department) {
        super(userId, username, password, fullName, email, isActive);
        this.managerId = managerId;
        this.department = department;
    }

    public int getManagerId() {
        return managerId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void addEmployee(Employee e) {
        if (!managedEmployees.contains(e)) {
            managedEmployees.add(e);
        }
    }

    public void removeEmployee(Employee e) {
        managedEmployees.remove(e);
    }

    public List<Employee> viewAllEmployees() {
        return managedEmployees;
    }

    public void generateReport() {
        // Module 1 chỉ in thông báo gọi sang Module 4 (Report) — chi tiết do Member 4 triển khai.
        System.out.println("[Manager] " + fullName + " yêu cầu tạo báo cáo doanh thu (chuyển sang Module 4 - ReportService).");
    }

    @Override
    public String getRole() {
        return "MANAGER";
    }

    /**
     * Format dòng lưu file (phân tách bằng '|'):
     * MANAGER|userId|username|password|fullName|email|isActive|managerId|department
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
                String.valueOf(managerId),
                department
        );
    }
}