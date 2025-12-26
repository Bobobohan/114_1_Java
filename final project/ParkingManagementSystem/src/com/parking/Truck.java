package com.parking;

public class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
    }

    @Override
    public double getHourlyRate() {
        return 300.0; // 300元/小時 [根據規格書]
    }
    @Override
    public double getHandicappedRate() {
        return 100.0; // SUV 身障費率 100元/小時
    }
}
