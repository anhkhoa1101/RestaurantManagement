/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantmanagement.model;

/**
 *
 * @author khoa0
 */
import java.util.ArrayList;
import java.util.List;

public class Employee extends User {

    private int employeeId;
    private String position;
    private double salary;
    private String hireDate;

    public Employee() {
    }

    public Employee(
            int userId,
            String username,
            String password,
            String fullName,
            String email,
            boolean isActive,
            int employeeId,
            String position,
            double salary,
            String hireDate
    ) {
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {

        if (salary < 0) {
            throw new IllegalArgumentException("Salary must be >= 0");
        }

        this.salary = salary;
    }

    public String getHireDate() {
        return hireDate;
    }

    public List<String> viewAssignedTables() {

        return new ArrayList<>();
    }

    @Override
    public String toString() {

        return "Employee{" +
                "employeeId=" + employeeId +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hireDate='" + hireDate + '\'' +
                "} " + super.toString();
    }
}