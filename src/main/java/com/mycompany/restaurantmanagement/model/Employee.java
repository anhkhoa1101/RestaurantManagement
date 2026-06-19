package com.mycompany.restaurantmanagement.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * [Member 1] Nhân viên phục vụ — xem bàn, gọi món, tạo đơn hàng.
 *
 * Lưu ý: UML dùng kiểu Date cho hireDate, ở đây dùng java.time.LocalDate
 * (API hiện đại, an toàn hơn) nhưng vẫn giữ đúng tên phương thức theo UML.
 */
public class Employee extends User {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int employeeId;
    private String position;
    private double salary;
    private LocalDate hireDate;

    // Danh sách bàn được giao phụ trách (liên kết với Module 3 - Table).
    // Lưu tableId thay vì object Table để tránh phụ thuộc cứng giữa các module
    // khi mỗi member phát triển độc lập.
    private List<Integer> assignedTableIds = new ArrayList<>();

    public Employee(int userId, String username, String password, String fullName,
                    String email, boolean isActive, int employeeId, String position,
                    double salary, LocalDate hireDate) {
        super(userId, username, password, fullName, email, isActive);
        this.employeeId = employeeId;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Lương không thể âm.");
        }
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public List<Integer> getAssignedTableIds() {
        return assignedTableIds;
    }

    public void assignTable(int tableId) {
        if (!assignedTableIds.contains(tableId)) {
            assignedTableIds.add(tableId);
        }
    }

    public void unassignTable(int tableId) {
        assignedTableIds.remove(Integer.valueOf(tableId));
    }

    /**
     * UML method: viewAssignedTables() : List~Table~
     * Module 1 chỉ trả về ID danh sách bàn đang phụ trách; việc lấy đối tượng Table
     * đầy đủ (trạng thái, sức chứa,...) thuộc trách nhiệm Module 3 (TableService).
     */
    public List<Integer> viewAssignedTables() {
        return assignedTableIds;
    }

    @Override
    public String getRole() {
        return "EMPLOYEE";
    }

    /**
     * Format dòng lưu file (phân tách bằng '|'):
     * EMPLOYEE|userId|username|password|fullName|email|isActive|employeeId|position|salary|hireDate(dd/MM/yyyy)|tableId1,tableId2,...
     */
    @Override
    public String toFileLine() {
        String tables = String.join(",", assignedTableIds.stream().map(String::valueOf).toArray(String[]::new));
        return String.join("|",
                getRole(),
                String.valueOf(userId),
                username,
                password,
                fullName,
                email,
                String.valueOf(isActive),
                String.valueOf(employeeId),
                position,
                String.valueOf(salary),
                hireDate.format(DATE_FORMAT),
                tables
        );
    }
}