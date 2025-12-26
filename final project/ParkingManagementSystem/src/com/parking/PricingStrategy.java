package com.parking;
import java.time.Duration;

public interface PricingStrategy {
    double calculateFee(Duration duration, Vehicle vehicle);
    String getDescription();
}
