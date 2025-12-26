package com.parking;

import java.time.LocalDateTime;

/**
 * ParkingTicket - 停車票券類別
 * 負責紀錄進場資訊、關聯車位與管理繳費狀態。
 */
public class ParkingTicket {
    // ========== 屬性 (完全貼合規格書) ==========
    private String ticketId;             // 唯一識別碼
    private LocalDateTime entryTime;      // 進場時間
    private Vehicle vehicle;              // 對應車輛
    private ParkingSpot assignedSpot;     // 分配的車位
    private boolean isPaid = false;       // 初始值為 false

    // ========== 建構子 ==========
    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot assignedSpot) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("不可為空或 null"); //
        }
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.assignedSpot = assignedSpot;
        this.entryTime = LocalDateTime.now();
    }

    // ========== 行為方法 ==========

    /**
     * 執行繳費
     * @param ticket 繳費票券
     */
    public void pay(ParkingTicket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("無效的停車票券或車牌號碼"); //
        }
        this.isPaid = true;
        System.out.println("✅ 票券 " + ticketId + " 已完成繳費。");
    }

    /**
     * 處理出場驗證
     * @param ticket 出場票券
     */
    public void processExit(ParkingTicket ticket) {
        // 檢查機制：若 ticket.isPaid 為 false，拋出 IllegalStateException
        if (!this.isPaid) {
            throw new IllegalStateException("此票券尚未完成繳費，無法離場"); //
        }
    }

    // ========== Getter 方法 ==========
    public String getTicketId() { return ticketId; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getAssignedSpot() { return assignedSpot; }
    public boolean isPaid() { return isPaid; }
}
