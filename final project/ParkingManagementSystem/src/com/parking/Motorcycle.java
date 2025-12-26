package com.parking;

public class Motorcycle extends Vehicle{
    public Motorcycle(String licensePlate) {
        super(licensePlate, VehicleType.MOTORCYCLE);
    }

    @Override
    public double getHourlyRate() {
        return 50.0; // 50元/小時 [根據規格書]
    }
    @Override
    public double getHandicappedRate() {
        return 100.0; // SUV 身障費率 100元/小時
    }
}
