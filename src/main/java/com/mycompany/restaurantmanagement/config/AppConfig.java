package com.mycompany.restaurantmanagement.config;

public class AppConfig {

    public static final String ORDERS_FILE_PATH           = "data/orders.txt";
    public static final String ORDER_DETAILS_FILE_PATH    = "data/order_details.txt";
    public static final String TABLES_FILE_PATH           = "data/tables.txt";
    public static final String MENU_FILE_PATH             = "data/menu_items.txt";
    public static final String INVENTORY_FILE_PATH        = "data/inventory.txt";
    public static final String USERS_FILE_PATH            = "data/users.txt";
    public static final String PAYMENT_FILE_PATH          = "data/payments.txt";
    public static final String CATEGORIES_FILE_PATH       = "data/categories.txt";
    public static final String INVOICE_FILE_PATH          = "data/invoice.txt";

    private AppConfig() {
        // Không cho khởi tạo — class chỉ chứa hằng số
    }
}