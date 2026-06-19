package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.Cashier;
import com.mycompany.restaurantmanagement.model.Employee;
import com.mycompany.restaurantmanagement.model.Manager;
import com.mycompany.restaurantmanagement.model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [Member 1] Repository chịu trách nhiệm đọc/ghi danh sách User từ/đến file users.txt.
 *
 * Toàn bộ dữ liệu được giữ trong bộ nhớ (List<User>) khi chương trình chạy;
 * loadFromFile() được gọi 1 lần lúc khởi động, saveToFile() được gọi sau mỗi
 * thay đổi (thêm/sửa/xóa user) để đảm bảo dữ liệu không mất khi tắt chương trình.
 */
public class UserRepository {

    private static final String DEFAULT_PATH = "data/users.txt";

    private final String filePath;
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        this(DEFAULT_PATH);
    }

    public UserRepository(String filePath) {
        this.filePath = filePath;
    }

    // ===================================================================
    // LOAD
    // ===================================================================

    /**
     * Đọc toàn bộ user từ file vào bộ nhớ.
     * - Nếu file chưa tồn tại: tạo thư mục/file rỗng, danh sách users để trống.
     * - Dòng rỗng hoặc bắt đầu bằng '#' sẽ được bỏ qua (comment).
     * - Dòng lỗi format sẽ được log ra console và bỏ qua, không làm crash toàn bộ load.
     */
    public List<User> loadFromFile() {
        users.clear();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.out.println("[UserRepository] Không tìm thấy " + filePath + " — sẽ tạo file mới khi save.");
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                try {
                    User user = parseLine(trimmed);
                    if (user != null) {
                        users.add(user);
                    }
                } catch (Exception e) {
                    System.out.println("[UserRepository] Lỗi parse dòng " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("[UserRepository] Lỗi đọc file " + filePath + ": " + e.getMessage());
        }

        System.out.println("[UserRepository] Đã load " + users.size() + " user từ " + filePath);
        return users;
    }

    /**
     * Parse 1 dòng file thành đối tượng User cụ thể (Manager/Employee/Cashier)
     * dựa trên field đầu tiên (role).
     */
    private User parseLine(String line) {
        String[] f = line.split("\\|");
        String role = f[0].trim();

        int userId = Integer.parseInt(f[1].trim());
        String username = f[2].trim();
        String password = f[3].trim();
        String fullName = f[4].trim();
        String email = f[5].trim();
        boolean isActive = Boolean.parseBoolean(f[6].trim());

        switch (role) {
            case "MANAGER": {
                int managerId = Integer.parseInt(f[7].trim());
                String department = f[8].trim();
                return new Manager(userId, username, password, fullName, email, isActive,
                        managerId, department);
            }
            case "EMPLOYEE": {
                int employeeId = Integer.parseInt(f[7].trim());
                String position = f[8].trim();
                double salary = Double.parseDouble(f[9].trim());
                LocalDate hireDate = LocalDate.parse(f[10].trim(), Employee.DATE_FORMAT);
                Employee employee = new Employee(userId, username, password, fullName, email,
                        isActive, employeeId, position, salary, hireDate);
                // field 11 (tableIds) có thể không tồn tại nếu rỗng ở cuối dòng
                if (f.length > 11 && !f[11].trim().isEmpty()) {
                    for (String idStr : f[11].trim().split(",")) {
                        employee.assignTable(Integer.parseInt(idStr.trim()));
                    }
                }
                return employee;
            }
            case "CASHIER": {
                int cashierId = Integer.parseInt(f[7].trim());
                LocalDateTime shiftStart = LocalDateTime.parse(f[8].trim(), Cashier.SHIFT_FORMAT);
                LocalDateTime shiftEnd = LocalDateTime.parse(f[9].trim(), Cashier.SHIFT_FORMAT);
                Cashier cashier = new Cashier(userId, username, password, fullName, email,
                        isActive, cashierId, shiftStart, shiftEnd);
                if (f.length > 10 && !f[10].trim().isEmpty()) {
                    cashier.recordRevenue(Double.parseDouble(f[10].trim()));
                }
                return cashier;
            }
            default:
                System.out.println("[UserRepository] Role không xác định: " + role + " — bỏ qua dòng.");
                return null;
        }
    }

    // ===================================================================
    // SAVE
    // ===================================================================

    /**
     * Ghi toàn bộ danh sách user hiện có trong bộ nhớ xuống file (overwrite).
     * Tự động tạo thư mục chứa file nếu chưa tồn tại.
     */
    public void saveToFile() {
        Path path = Paths.get(filePath);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            System.out.println("[UserRepository] Không thể tạo thư mục chứa file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), false))) {
            writer.write("# users.txt — auto-generated by UserRepository.saveToFile()");
            writer.newLine();
            for (User u : users) {
                writer.write(u.toFileLine());
                writer.newLine();
            }
            System.out.println("[UserRepository] Đã lưu " + users.size() + " user vào " + filePath);
        } catch (IOException e) {
            System.out.println("[UserRepository] Lỗi ghi file " + filePath + ": " + e.getMessage());
        }
    }

    // ===================================================================
    // CRUD trên danh sách trong bộ nhớ (chưa lưu file — service sẽ gọi saveToFile())
    // ===================================================================

    public List<User> findAll() {
        return users;
    }

    public User findById(int userId) {
        return users.stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    public List<Manager> findAllManagers() {
        return users.stream()
                .filter(u -> u instanceof Manager)
                .map(u -> (Manager) u)
                .collect(Collectors.toList());
    }

    public List<Employee> findAllEmployees() {
        return users.stream()
                .filter(u -> u instanceof Employee)
                .map(u -> (Employee) u)
                .collect(Collectors.toList());
    }

    public List<Cashier> findAllCashiers() {
        return users.stream()
                .filter(u -> u instanceof Cashier)
                .map(u -> (Cashier) u)
                .collect(Collectors.toList());
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username) != null;
    }

    public void add(User user) {
        users.add(user);
    }

    public boolean remove(int userId) {
        return users.removeIf(u -> u.getUserId() == userId);
    }

    /**
     * Sinh userId mới = max(userId hiện có) + 1. Trả về 1 nếu danh sách rỗng.
     */
    public int generateNextUserId() {
        return users.stream()
                .mapToInt(User::getUserId)
                .max()
                .orElse(0) + 1;
    }
}