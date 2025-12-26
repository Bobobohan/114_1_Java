package com.parking;
import java.time.Duration;

public class PenaltyPricing implements PricingStrategy{
    @Override
    public double calculateFee(Duration duration, Vehicle vehicle) {
        long hours = (duration.toMinutes() + 59) / 60;
        // 罰金邏輯：(標準每小時費率 + 100) * 總時數
        double penaltyRate = vehicle.getHourlyRate() + 100.0;
        return hours * penaltyRate;
    }

    @Override
    public String getDescription() {
        return "超時處罰計費 (標準費率+100)";
    }
}
