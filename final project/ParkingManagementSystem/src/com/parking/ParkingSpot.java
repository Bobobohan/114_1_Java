package com.parking;

public abstract class ParkingSpot {
    private String spotId;
    private boolean isOccupied;

    public ParkingSpot(String spotId) {
        if (spotId == null || spotId.trim().isEmpty()) {
            throw new IllegalArgumentException("不可為空或 null");
        }
        this.spotId = spotId;
        this.isOccupied = false;
    }

    public void validateVehicleAccess(Vehicle vehicle) {
        // 機車專屬位檢查
        if (this instanceof MotorcycleSpot && vehicle.getType() != VehicleType.MOTORCYCLE) {
            throw new IllegalArgumentException("此車種不符合該車位類型限制，請停往專屬區域");
        }
        // 大型位檢查
        if (this instanceof LargeSpot && vehicle.getType() != VehicleType.SUV) {
            throw new IllegalArgumentException("此車種不符合該車位類型限制，請停往專屬區域");
        }
        // 電動位檢查
        if (this instanceof ElectricSpot && !(vehicle instanceof Chargeable)) {
            throw new IllegalArgumentException("此車位僅供電動車使用");
        }
        // 貨車位置輔助檢查
        if (vehicle.getType() == VehicleType.TRUCK && !(this instanceof RegularSpot)) {
            throw new IllegalArgumentException("此車種不符合該車位類型限制，請停往專屬區域");
        }
    }

    // 取得車位編號
    public String getSpotId() {
        return spotId;
    }

    // 取得目前是否被佔用
    public boolean isOccupied() {
        return isOccupied;
    }

    // 設定車位佔用狀態 (供預約鎖定使用)
    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

}