/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.restaurantmanagement;

import com.mycompany.restaurantmanagement.repository.*;
import com.mycompany.restaurantmanagement.service.*;
import com.mycompany.restaurantmanagement.ui.*;
/**
 *
 * @author khoa0
 */
public class RestaurantManagement {

    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository_File();
        AuthService authService = new AuthService(userRepository);
        Router router = new Router();
        LoginUI loginUI = new LoginUI(authService, router);

        loginUI.show();
    }
}
