package com.parking;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * ParkingSession - 停車紀錄類別
 * 用途：用於事後報表查詢與歷史紀錄，以及判斷再進場狀態 。
 */
public class ParkingSession {
    // ========== 屬性 (完全貼合規格書) ==========
    private String ticketId;             // 票券識別碼 (不可為空或 null)
    private LocalDateTime exitTime;      // 出場時間 (不可為 null)
    private double totalFee;             // 總費用
    private String paymentStatus;        // 付款狀態 (不可為 null)
    private Duration totalDurationUsed;  // 當前有效週期內已使用的總時數
    private LocalDateTime lastExitTime;  // 最後一次駛離的時間

    // ========== 建構子 ==========
    public ParkingSession(String ticketId, LocalDateTime exitTime, double totalFee, String paymentStatus) {
        // 驗證屬性不可為空或 null
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("ticketId 不可為空或 null ");
        }
        if (exitTime == null || paymentStatus == null) {
            throw new IllegalArgumentException("exitTime 與 paymentStatus 不可為 null ");
        }

        this.ticketId = ticketId;
        this.exitTime = exitTime;
        this.totalFee = totalFee;
        this.paymentStatus = paymentStatus;
        this.lastExitTime = exitTime; // 初始化最後駛離時間為本次出場時間
    }

    // ========== Getter 方法 ==========
    public String getTicketId() { return ticketId; }
    public LocalDateTime getExitTime() { return exitTime; }
    public double getTotalFee() { return totalFee; }
    public String getPaymentStatus() { return paymentStatus; }
    public Duration getTotalDurationUsed() { return totalDurationUsed; }
    public LocalDateTime getLastExitTime() { return lastExitTime; }

    // ========== Setter 方法 (用於更新週期累積時數) ==========
    public void setTotalDurationUsed(Duration totalDurationUsed) {
        this.totalDurationUsed = totalDurationUsed;
    }
}
