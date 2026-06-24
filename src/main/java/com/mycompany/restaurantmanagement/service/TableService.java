package com.mycompany.restaurantmanagement.service;

import com.mycompany.restaurantmanagement.model.Table;
import com.mycompany.restaurantmanagement.repository.TableRepository;

import java.util.List;

/**
 * Service xử lý nghiệp vụ quản lý bàn.
 * <p>
 * Chức năng:
 * - Lấy danh sách bàn
 * - Kiểm tra bàn trống
 * - Gán bàn cho Order
 * - Giải phóng bàn sau thanh toán
 * - Thêm bàn mới
 */
public class TableService {

    // Repository đọc/ghi dữ liệu bàn từ file txt
    private TableRepository tableRepository;

    /**
     * Inject TableRepository
     */
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    /**
     * Lấy toàn bộ danh sách bàn.
     *
     * @return danh sách tất cả bàn
     */
    public List<Table> getAllTables() {

        return tableRepository.findAll();
    }

    /**
     * Lấy các bàn đang còn trống.
     * <p>
     * Điều kiện:
     * occupied = false
     *
     * @return danh sách bàn khả dụng
     */
    public List<Table> getAvailableTables() {

        return tableRepository.findAvailable();
    }

    /**
     * Gán bàn cho khách sử dụng.
     * <p>
     * Quy trình:
     * 1. Tìm bàn theo ID
     * 2. Kiểm tra tồn tại
     * 3. Kiểm tra bàn đang trống
     * 4. Đánh dấu đã sử dụng
     * 5. Lưu cập nhật
     *
     * @param tableId mã bàn
     * @return true nếu thành công
     */
    public boolean assignTable(int tableId) {

        Table table = tableRepository.findById(tableId);

        // không tồn tại
        if (table == null) {
            return false;
        }

        // bàn đã có khách
        if (table.isOccupied()) {
            return false;
        }

        // chuyển trạng thái
        table.setOccupied(true);

        return tableRepository.update(table);
    }

    /**
     * Giải phóng bàn.
     * <p>
     * Được gọi khi:
     * - Thanh toán
     * - Huỷ Order
     * <p>
     * Quy trình:
     * 1. Tìm bàn
     * 2. Kiểm tra tồn tại
     * 3. Kiểm tra đang sử dụng
     * 4. Chuyển sang trạng thái trống
     * 5. Cập nhật dữ liệu
     *
     * @param tableId mã bàn
     * @return true nếu thành công
     */
    public boolean releaseTable(int tableId) {

        Table table = tableRepository.findById(tableId);

        // không tìm thấy bàn
        if (table == null) {
            return false;
        }

        // bàn đang trống
        if (!table.isOccupied()) {
            return false;
        }

        // giải phóng
        table.setOccupied(false);

        return tableRepository.update(table);
    }

    /**
     * Thêm bàn mới.
     * <p>
     * Điều kiện:
     * - tên không rỗng
     * - sức chứa > 0
     * <p>
     * Quy trình:
     * 1. Validate dữ liệu
     * 2. Sinh tableId
     * 3. Tạo bàn
     * 4. Lưu xuống file
     *
     * @param name     tên bàn
     * @param capacity số người tối đa
     * @return bàn vừa tạo
     */
    public Table addTable(String name, int capacity) {

        if (name == null || name.trim().isEmpty()) {

            throw new IllegalArgumentException("Tên bàn không được để trống");
        }

        if (capacity <= 0) {

            throw new IllegalArgumentException("Sức chứa phải > 0");
        }

        // sinh id
        int newId = tableRepository.findAll().size() + 1;

        Table table = new Table(newId, name, capacity, false);

        tableRepository.add(table);

        return table;
    }

}