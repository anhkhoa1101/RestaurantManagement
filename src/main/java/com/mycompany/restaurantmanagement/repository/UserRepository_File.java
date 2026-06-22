package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.*;
import java.io.*;
import java.util.*;

public class UserRepository_File implements UserRepository {
    private static final String FILE_PATH = "data/users.txt";
    private List<User> users = new ArrayList<>();

    public UserRepository_File() {
        loadFromFile();
    }

    // Đọc toàn bộ file -> nạp vào List (cache trong RAM để truy cập nhanh)
    private void loadFromFile() {
        users.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] f = line.split("\\|");
                int id = Integer.parseInt(f[0]);
                String username = f[1];
                String password = f[2];
                String fullName = f[3];
                String email = f[4];
                Role role = Role.valueOf(f[5]);

                users.add(createUserByRole(role, id, username, password, fullName, email));
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file users.txt: " + e.getMessage());
        }
    }

    // Tạo đúng đối tượng con (Manager/Employee/Cashier) dựa theo Role
    private User createUserByRole(Role role, int id, String username, String password,
                                  String fullName, String email) {
        switch (role) {
            case MANAGER:  return new Manager(id, username, password, fullName, email);
            case EMPLOYEE: return new Employee(id, username, password, fullName, email);
            case CASHIER:  return new Cashier(id, username, password, fullName, email);
            default: throw new IllegalArgumentException("Role không hợp lệ: " + role);
        }
    }

    // Ghi đè toàn bộ List xuống file (đơn giản hơn so với sửa từng dòng)
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                bw.write(u.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file users.txt: " + e.getMessage());
        }
    }

    @Override
    public User findByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    @Override
    public void save(User user) {
        users.add(user);
        saveToFile();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == user.getUserId()) {
                users.set(i, user);
                break;
            }
        }
        saveToFile();
    }

    @Override
    public void delete(int userId) {
        users.removeIf(u -> u.getUserId() == userId);
        saveToFile();
    }
}