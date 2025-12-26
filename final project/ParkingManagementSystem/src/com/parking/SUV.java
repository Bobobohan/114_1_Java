package com.parking;

public class SUV extends Vehicle {
    public SUV(String licensePlate) {
        super(licensePlate, VehicleType.SUV);
    }

    @Override
    public double getHourlyRate() {
        return 200.0; // 200元/小時 [根據規格書]
    }
    @Override
    public double getHandicappedRate() {
        return 100.0; // SUV 身障費率 100元/小時
    }
}
