package com.parking;

public class Sedan extends Vehicle {
    public Sedan(String licensePlate) {
        super(licensePlate, VehicleType.SEDAN);
    }

    @Override
    public double getHourlyRate() {
        return 100.0; // 100元/小時 [根據規格書]
    }
    @Override
    public double getHandicappedRate() {
        return 100.0; // SUV 身障費率 100元/小時
    }
}
