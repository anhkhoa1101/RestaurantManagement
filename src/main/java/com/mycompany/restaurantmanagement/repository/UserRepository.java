package com.mycompany.restaurantmanagement.repository;

import com.mycompany.restaurantmanagement.model.*;
import com.mycompany.restaurantmanagement.config.AppConfig;
public class UserRepository extends BaseRepository<User, Integer> {

    public UserRepository() {
        super(AppConfig.USERS_FILE_PATH);
    }

    @Override
    public User findById(Integer id) {

        for (User user : data) {

            if (user.getUserId() == id) {
                return user;
            }
        }

        return null;
    }

    public User findByUsername(String username) {

        for (User user : data) {

            if (user.getUsername() != null
                    && user.getUsername().equalsIgnoreCase(username)) {

                return user;
            }
        }

        return null;
    }

    public void update(User updatedUser) {

        for (int i = 0; i < data.size(); i++) {

            User current = data.get(i);

            if (current.getUserId() == updatedUser.getUserId()) {

                data.set(i, updatedUser);

                saveToFile();

                return;
            }
        }

        System.out.println("Không tìm thấy user để update.");
    }

    @Override
    protected User parseLine(String line) {
        String[] parts = line.split("\\|");

        int id = Integer.parseInt(parts[0]);
        String username = parts[1];
        String password = parts[2];
        String fullName = parts[3];
        String email = parts[4];
        Role role = Role.valueOf(parts[5]);

        switch (role) {
            case MANAGER:  return new Manager(id, username, password, fullName, email);
            case WAREHOUSE: return new WarehouseManager(id, username, password, fullName, email);
            case EMPLOYEE: return new Employee(id, username, password, fullName, email);
            case CASHIER:  return new Cashier(id, username, password, fullName, email);
            default: throw new IllegalArgumentException("Role không hợp lệ: " + role);
        }
    }

    @Override
    protected String toLine(User user) {

        return user.getUserId() + "|"
                + user.getUsername() + "|"
                + user.getPassword() + "|"
                + user.getFullName() + "|"
                + user.getEmail() + "|"
                + user.getRole();
    }
}