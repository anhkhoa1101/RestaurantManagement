package com.mycompany.restaurantmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    private int managerId;
    private String department;

    private List<Employee> employees = new ArrayList<>();

    public Manager() {
    }

    public Manager(
            int userId,
            String username,
            String password,
            String fullName,
            String email,
            boolean isActive,
            int managerId,
            String department
    ) {

        super(userId, username, password,
                fullName, email, isActive);

        this.managerId = managerId;
        this.department = department;
    }

    public int getManagerId() {
        return managerId;
    }

    public String getDepartment() {
        return department;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void removeEmployee(Employee e) {
        employees.remove(e);
    }

    public List<Employee> viewAllEmployees() {
        return employees;
    }

    public void generateReport() {
        System.out.println("Generating report...");
    }

    @Override
    public String toString() {

        return "Manager{" +
                "managerId=" + managerId +
                ", department='" + department + '\'' +
                "} " + super.toString();
    }
}
