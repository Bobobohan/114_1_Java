package com.parking;
import java.time.Duration;

public abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;
    private boolean isOvertimeRegistered = false;

    public Vehicle(String licensePlate, VehicleType type) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("車牌不可為空或 null");
        }
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public abstract double getHourlyRate();
    public abstract double getHandicappedRate(); // 符合規格書身障優惠需求

    public void validateDuration(Duration duration, String strategyName) {
        // DailyMax 或 Progressive 策略下限制 12 小時
        if (duration.toHours() > 12 && (strategyName.contains("DailyMax") || strategyName.contains("Progressive"))) {
            throw new IllegalStateException("停車時間超過 12 小時上限");
        }
    }

    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }
    public boolean isOvertimeRegistered() { return isOvertimeRegistered; }
    public void setOvertimeRegistered(boolean registered) { this.isOvertimeRegistered = registered; }
}