package com.parking;
import java.time.Duration;

public class DailyMaxPricing implements PricingStrategy {
    @Override
    public double calculateFee(Duration duration, Vehicle vehicle) {
        long hours = (duration.toMinutes() + 59) / 60;
        if (hours > 12) {
            throw new IllegalStateException("停車時間超過 12 小時上限");
        }
        return vehicle.getHourlyRate() * 12;
    }

    @Override
    public String getDescription() {
        return "上限計費";
    }
}
