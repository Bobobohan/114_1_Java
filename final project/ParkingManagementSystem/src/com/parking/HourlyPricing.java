package com.parking;
import java.time.Duration;

public class HourlyPricing implements PricingStrategy {
    @Override
    public double calculateFee(Duration duration, Vehicle vehicle) {
        // 未滿一小時以一小時計：((minutes + 59) / 60)
        long hours = (duration.toMinutes() + 59) / 60;
        return hours * vehicle.getHourlyRate();
    }

    @Override
    public String getDescription() {
        return "標準計費";
    }
}
