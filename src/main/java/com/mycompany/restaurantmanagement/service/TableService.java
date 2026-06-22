package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.TableRepository;

import java.util.List;

/**
 * [Member 3] Xử lý logic nghiệp vụ liên quan đến Bàn ăn.
 * Bao gồm: xem danh sách bàn, đặt bàn, trả bàn.
 */
public class TableService {

    // Kho dữ liệu bàn (đọc/ghi file)
    private TableRepository tableRepo;

    public TableService(TableRepository tableRepo) {
        this.tableRepo = tableRepo;
    }

    /**
     * Lấy toàn bộ danh sách bàn (để hiển thị sơ đồ bàn).
     */
    public List<Table> getAllTables() {
        return tableRepo.findAll();
    }

    /**
     * Tìm bàn theo ID.
     * @return Table hoặc null nếu không tồn tại
     */
    public Table getTableById(int id) {
        return tableRepo.findById(id);
    }

    /**
     * Đặt bàn: chỉ được đặt khi bàn đang trống.
     * @return true nếu đặt thành công, false nếu bàn đã có khách
     */
    public boolean occupyTable(int tableId) {
        Table t = tableRepo.findById(tableId);
        if (t == null) {
            System.out.println("Không tìm thấy bàn #" + tableId);
            return false;
        }
        if (t.isOccupied()) {
            System.out.println("Bàn " + t.getTableName() + " đang có khách!");
            return false;
        }
        // Đặt trạng thái có khách và lưu file
        t.setOccupied(true);
        tableRepo.update();
        System.out.println("✅ Đã đặt " + t.getTableName());
        return true;
    }

    /**
     * Trả bàn: sau khi thanh toán xong, đặt bàn về trống.
     */
    public void freeTable(int tableId) {
        Table t = tableRepo.findById(tableId);
        if (t != null) {
            t.setOccupied(false);
            tableRepo.update();
            System.out.println("✅ " + t.getTableName() + " đã được trả về trống.");
        }
    }

    /**
     * In sơ đồ tất cả bàn ra console.
     */
    public void printTableMap() {
        System.out.println("\n====== SƠ ĐỒ BÀN ======");
        List<Table> all = tableRepo.findAll();
        if (all.isEmpty()) {
            System.out.println("  (Chưa có bàn nào)");
        }
        for (Table t : all) {
            System.out.println("  " + t);
        }
        System.out.println("========================");
    }
}
