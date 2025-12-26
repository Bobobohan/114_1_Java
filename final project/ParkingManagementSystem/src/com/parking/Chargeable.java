package com.parking;
import java.time.Duration;

public interface Chargeable {
    void startCharging(Vehicle vehicle);
    double getChargingFee(Duration duration); // 每小時 50
}
